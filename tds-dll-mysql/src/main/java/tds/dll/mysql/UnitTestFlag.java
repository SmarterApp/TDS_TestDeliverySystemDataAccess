/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.mysql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import TDS.Shared.Exceptions.ReturnStatusException;
import tds.dll.api.IUnitTestFlag;
import AIR.Common.DB.AbstractDLL;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.SqlParametersMaps;
import AIR.Common.DB.results.DbResultRecord;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Sql.AbstractDateUtilDll;

/**
 * @author akulakov
 * 
 */
public class UnitTestFlag extends AbstractDLL implements IUnitTestFlag
{
  private static Logger                     _logger                = LoggerFactory.getLogger (UnitTestFlag.class);

  @Autowired
  private AbstractDateUtilDll               dateUtil               = null;

  final String                              root                   = "C:\\java_workspace\\TdsDLLDev\\database scripts - mysql\\test data for unit tests db\\";
  final String                              tablesStatFileName     = "table statistics.csv";
  final String                              unittestflagcolumnName = "unittestflag";                                                                          // "unittestflag2"

  String                                    queryCheckColumn       = "SELECT count(*) as count FROM information_schema.COLUMNS "
                                                                       + "WHERE TABLE_SCHEMA = ${dbname} AND TABLE_NAME = ${tablename} "
                                                                       + "AND COLUMN_NAME = '" + unittestflagcolumnName + "'";

  // here String is children table name
  private Map<String, Set<TableConstraint>> tblConstraints         = null;
  // names of all children
  private Set<String>                       childrenNames          = null;
  // names of all parents
  private Set<String>                       parentNames            = null;

  public void statisticsUTFs (SQLConnection connection) throws Exception, IOException
  {
    /**
     * select table_name from information_schema.tables where table_schema =
     * 'tdscore_dev_session2012_sandbox';
     * 
     * SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey",
     * oppkey);
     * 
     * SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = 'db_name'
     * AND TABLE_NAME = 'table_name' AND COLUMN_NAME = 'column_name';
     * 
     * select table_name from information_schema.tables where table_schema =
     * 'tdscore_test_session' and table_type <> 'VIEW'
     */
    long allDB = 0;
    long unittestflagDB = 0;
    long allDBs = 0;
    long unittestflagDBs = 0;
    final String comma = ",";

    try
    {
      String statQUERY = "select count(*) as count from  ";
      String statCondition = " where " + unittestflagcolumnName + " = 1";
      String query = null;
      String tablename = null;
      long countAll = 0;
      long countTest = 0;
      SingleDataResultSet result = null;
      SingleDataResultSet result2 = null;
      DbResultRecord record = null;
      DbResultRecord record2 = null;
      SqlParametersMaps parameters = new SqlParametersMaps ();

      for (String dbname : getDBNames ())
      {
        String fileName = root + dbname + "_" + tablesStatFileName;
        String text = "Table name, all records, " + unittestflagcolumnName + " records";
        appendFile (fileName, text);

        System.out.println ("dbname = " + dbname);
        appendFile (fileName, dbname);
        allDB = 0;
        unittestflagDB = 0;

        query = getTableNamesInDB (dbname);
        parameters.put ("dbname", dbname);
        result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
        Iterator<DbResultRecord> records = result.getCount () > 0 ? result.getRecords () : null;
        if (records != null)
        {
          while (records.hasNext ())
          {
            record = records.next ();
            if (record != null) {
              tablename = record.<String> get ("table_name");
              parameters.put ("tablename", tablename);
              _logger.info ("tableName = " + tablename);

              result2 = executeStatement (connection, queryCheckColumn, parameters, false).getResultSets ().next ();
              record2 = result2.getCount () > 0 ? result2.getRecords ().next () : null;
              text = tablename;
              if (record2 != null) {
                countAll = record2.<Long> get ("count");
              }
              if (countAll > 0)
              {
                query = statQUERY + dbname + "." + tablename;
                result2 = executeStatement (connection, query, parameters, false).getResultSets ().next ();
                record2 = result2.getCount () > 0 ? result2.getRecords ().next () : null;
                if (record2 != null) {
                  countAll = record2.<Long> get ("count");
                }
                query = query + statCondition;
                result2 = executeStatement (connection, query, parameters, false).getResultSets ().next ();
                record2 = result2.getCount () > 0 ? result2.getRecords ().next () : null;
                if (record2 != null) {
                  countTest = record2.<Long> get ("count");
                }
                _logger.info ("All = " + countAll + "; Test = " + countTest);
                text = text + comma + countAll + comma + countTest;
                appendFile (fileName, text);
                allDB += countAll;
                unittestflagDB += countTest;
              }
            }
          }
        }
        text = dbname + comma + allDB + comma + unittestflagDB;
        appendFile (fileName, text);
        allDBs += allDB;
        unittestflagDBs += unittestflagDB;
      }
      _logger.info ("all dbs = " + allDBs + "; unittestflag = " + unittestflagDBs);
    } catch (Exception e) {

    }
  }

  public void createUnitTestFlag (SQLConnection connection) throws Exception, IOException
  {

    String query = null;
    try {

      String alterQuery = "alter table ";// tdscore_test_configs._messageid add
                                         // unittestflag int null";
      String add = " add ";
      String type = " int null";

      String tablename = null;
      long countCol = 0;
      SingleDataResultSet result = null;
      SingleDataResultSet result2 = null;
      DbResultRecord record = null;
      DbResultRecord record2 = null;
      SqlParametersMaps parameters = new SqlParametersMaps ();

      for (String dbname : getDBNames ())
      {

        query = getTableNamesInDB (dbname);
        parameters.put ("dbname", dbname);
        result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
        Iterator<DbResultRecord> records = result.getCount () > 0 ? result.getRecords () : null;
        if (records != null)
        {
          while (records.hasNext ())
          {
            record = records.next ();
            if (record != null) {
              tablename = record.<String> get ("table_name");
              parameters.put ("tablename", tablename);
              _logger.info ("tableName = " + tablename);

              result2 = executeStatement (connection, queryCheckColumn, parameters, false).getResultSets ().next ();
              record2 = result2.getCount () > 0 ? result2.getRecords ().next () : null;
              if (record2 != null) {
                countCol = record2.<Long> get ("count");
              }
              if (countCol == 0)
              {
                query = alterQuery + dbname + "." + tablename + add + unittestflagcolumnName + type;
                int upCount = executeStatement (connection, query, parameters, true).getUpdateCount ();
                _logger.info ("Updated " + upCount + " rows in createUnitTestFlag method.");
              }

            }
          }
        }
      }
    } catch (Exception e) {
      _logger.error ("Query: " + query + ", Exception: " + e.getMessage ());
    }
  }

  //
  public void dropUnitTestFlag (SQLConnection connection) throws Exception, IOException
  {
    String query = null;
    try {

      String alterQuery = "alter table ";// tdscore_test_configs._messageid add
                                         // unittestflag int null";
      String drop = " drop ";

      String tablename = null;
      long countCol = 0;
      SingleDataResultSet result = null;
      SingleDataResultSet result2 = null;
      DbResultRecord record = null;
      DbResultRecord record2 = null;
      SqlParametersMaps parameters = new SqlParametersMaps ();

      for (String dbname : getDBNames ())
      {

        query = getTableNamesInDB (dbname);
        parameters.put ("dbname", dbname);
        result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
        Iterator<DbResultRecord> records = result.getCount () > 0 ? result.getRecords () : null;
        if (records != null)
        {
          while (records.hasNext ())
          {
            record = records.next ();
            if (record != null) {
              tablename = record.<String> get ("table_name");
              parameters.put ("tablename", tablename);
              _logger.info ("tableName = " + tablename);

              result2 = executeStatement (connection, queryCheckColumn, parameters, false).getResultSets ().next ();
              record2 = result2.getCount () > 0 ? result2.getRecords ().next () : null;
              if (record2 != null) {
                countCol = record2.<Long> get ("count");
              }
              if (countCol > 0)
              {
                query = alterQuery + dbname + "." + tablename + drop + unittestflagcolumnName;
                int upCount = executeStatement (connection, query, parameters, true).getUpdateCount ();
                _logger.info ("Updated " + upCount + " rows in dropUnitTestFlag method.");
              }
            }
          }
        }
      }
    } catch (Exception e) {
      _logger.error ("Query: " + query + ", Exception: " + e.getMessage ());
    }
  }

  //
  public void executeStatementsFromFile (SQLConnection connection, String filePath) throws Exception, IOException
  {
    SqlParametersMaps parameters = new SqlParametersMaps ();

    BufferedReader br = new BufferedReader (new FileReader (filePath));
    String line;
    while ((line = br.readLine ()) != null) {
      try {
        // one line -- one update query
        int upCount = executeStatement (connection, line, parameters, true).getUpdateCount ();
        _logger.info ("Query: " + line);
        _logger.info ("Updated " + upCount + " rows in previous query");
      } catch (Exception e)
      {
        _logger.error ("Error occured with query: " + line + "; Exception: " + e.getMessage ());
      }
    }
    br.close ();
  }

   //
  public void propagateUnittestflag (SQLConnection connection)
  {
    for (String dbname : getDBNames ())
    {
      tblConstraints         = new HashMap<String, Set<TableConstraint>> ();
      childrenNames          = new HashSet<String> ();
      parentNames            = new HashSet<String> ();
      _logger.info ("dbname = " + dbname);
      propagateUnittestflagOnDB (connection, dbname);
    }
  }

  //===========================================================================
  //
  private Set<String> getDBNames ()
  {
    Set<String> dbnames = new HashSet<String> ();

    String configsName = "tdscore_test_configs";
    dbnames.add (configsName);

    String itembankName = "tdscore_test_itembank";
    dbnames.add (itembankName);

    String sessionName = "tdscore_test_session";
    dbnames.add (sessionName);

    return dbnames;
  }
  
  // return query which get list of tables (not views) in input DB
  private String getTableNamesInDB (String dbname)
  {
    return "select table_name from information_schema.tables where table_schema = \'"
        + dbname + "\'"
        + "  and table_type <> 'VIEW'";
  }
  // It is not test
  private void appendFile (String fileName, String text)
  {
    final String ls = System.getProperty ("line.separator");
    try
    {
      FileWriter fw = new FileWriter (fileName, true); // the true will append
                                                       // the new data
      fw.write (text); // appends the string to the file
      fw.write (ls);

      fw.close ();
    } catch (IOException ioe)
    {
      System.err.println ("IOException: " + ioe.getMessage ());
    }
  }

  //
  public void propagateUnittestflagOnDB (SQLConnection connection, String dbname)
  {
    // gatering of the information
    createDBConstraints (connection, dbname);
    
    Set<String> currentLeaves = findLeaves (tblConstraints, parentNames);
    Map<String, Set<TableConstraint>> currentTblConstraints = new HashMap<String, Set<TableConstraint>> (tblConstraints);
    while (!currentTblConstraints.isEmpty ())
    {
      for (String leaf : currentLeaves)
      {
        for (TableConstraint tblConstraint : currentTblConstraints.get (leaf))
        {
          propagateUnittestflagOnTable (connection, tblConstraint, dbname);
        }
        currentTblConstraints.remove (leaf);
      }
      currentLeaves = getNewLeaves (currentTblConstraints);
    }
  }
  //
  private void propagateUnittestflagOnTable (SQLConnection connection, TableConstraint tblConstraint, String dbname)
  {
    Map<String, String> unquotedparms = new HashMap<String, String> ();
    unquotedparms.put ("ptblName", dbname + "." + tblConstraint.getParentName ());
    unquotedparms.put ("ctblName", dbname + "." + tblConstraint.getChildName ());

    String wherePClause = " where (";
    int cnt = 0;
    for (String fk : tblConstraint.getParent ().getFkeys ())
    {
      if (cnt > 0)
      {
        wherePClause = wherePClause + ", ";
      }
      wherePClause = wherePClause + "P." + fk;
      cnt++;
    }
    wherePClause = wherePClause + ") ";
    //
    String selctCClause = " ";
    cnt = 0;
    for (String fk : tblConstraint.getChild ().getFkeys ())
    {
      if (cnt > 0)
      {
        selctCClause = selctCClause + ", ";
      }
      selctCClause = selctCClause + "C." + fk;
      cnt++;
    }

    SqlParametersMaps parameters = new SqlParametersMaps ();
    String query = "update ${ptblName} P set P." + unittestflagcolumnName + " = 1 " + wherePClause + " in (select " + selctCClause + " from  ${ctblName} C where C." + unittestflagcolumnName + " = 1) ";
    try {
      int insertedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, false).getUpdateCount ();
    } catch (ReturnStatusException e) {
      _logger.error (e.getMessage ());
    }
  }

  //
  private Set<String> getNewLeaves (Map<String, Set<TableConstraint>> currentTblConstraints)
  {
    // names of all parents in currentTblConstraints
    Set<String> prntNames = new HashSet<String> ();
    for (String chtblName : currentTblConstraints.keySet ())
    {
      for (TableConstraint tblCnstrnt : currentTblConstraints.get (chtblName))
      {
        prntNames.add (tblCnstrnt.getParentName ());
      }
    }
    return findLeaves (currentTblConstraints, prntNames);
  }

  //
  private Set<String> findLeaves (Map<String, Set<TableConstraint>> currentTblConstraints, Set<String> prntNames)
  {
    Set<String> leaves = new HashSet<String> ();
    for (String tblName : currentTblConstraints.keySet ())
    {
      if (!prntNames.contains (tblName))
      {
        leaves.add (tblName);
      }
    }
    return leaves;
  }

  //
  private void createDBConstraints (SQLConnection connection, String dbname)
  {
    // here String is cildren table name
    String constraintname = null;
    String tablename = null;
    String referencedTablename = null;
    String columnname = null;
    String referencedColumnname = null;

    final String query = "select distinct constraint_name, TABLE_NAME, REFERENCED_TABLE_NAME "
        + " from INFORMATION_SCHEMA.KEY_COLUMN_USAGE where table_schema = ${dbname} "
        + " and REFERENCED_TABLE_NAME is not null group by constraint_name";

    final String query2 = "select  COLUMN_NAME, "
        + " REFERENCED_COLUMN_NAME "
        + " from INFORMATION_SCHEMA.KEY_COLUMN_USAGE where table_schema = ${dbname} and constraint_name = ${constraint_name}";

    SqlParametersMaps parameters = new SqlParametersMaps ().put ("dbname", dbname);
    SingleDataResultSet result = null;

    SingleDataResultSet result2 = null;
    DbResultRecord record2 = null;

    try {
      result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
    } catch (ReturnStatusException e) {
      _logger.error (e.getMessage ());
    }
    Iterator<DbResultRecord> records = result.getCount () > 0 ? result.getRecords () : null;
    DbResultRecord record = null;
    if (records != null)
    {
      while (records.hasNext ())
      {
        record = records.next ();
        if (record != null) {
          constraintname = record.<String> get ("constraint_name");
          tablename = record.<String> get ("TABLE_NAME");
          referencedTablename = record.<String> get ("REFERENCED_TABLE_NAME");
          parameters.put ("constraint_name", constraintname);
          try {
            result2 = executeStatement (connection, query2, parameters, false).getResultSets ().next ();
          } catch (ReturnStatusException e) {
            _logger.error (e.getMessage ());
          }
          Iterator<DbResultRecord> records2 = result2.getCount () > 0 ? result2.getRecords () : null;
          List<String> columnNames = new ArrayList<String> ();
          List<String> refColumnNames = new ArrayList<String> ();
          if (records2 != null)
          {
            while (records2.hasNext ())
            {
              try {
                record2 = records2.next ();
                if (record2 != null) {
                  columnname = record2.<String> get ("COLUMN_NAME");
                  referencedColumnname = record2.<String> get ("REFERENCED_COLUMN_NAME");
                  columnNames.add (columnname);
                  refColumnNames.add (referencedColumnname);
                }
              } catch (Exception e) {
                _logger.error (e.getMessage ());
              }

            }
          }
          childrenNames.add (tablename);
          parentNames.add (referencedTablename);
          TableFKeys parenttbl = new TableFKeys (referencedTablename, refColumnNames);
          TableFKeys childtbl = new TableFKeys (tablename, columnNames);
          TableConstraint tblConstrnt = new TableConstraint (constraintname, childtbl, parenttbl);
          if (tblConstraints.containsKey (tablename))
          {
            Set<TableConstraint> tmp = tblConstraints.get (tablename);
            tmp.add (tblConstrnt);
            tblConstraints.put (tablename, tmp);
          } else
          {
            Set<TableConstraint> tmp = new HashSet<TableConstraint> ();
            tmp.add (tblConstrnt);
            tblConstraints.put (tablename, tmp);
          }
        }
      }
    }

  }

  class TableConstraint
  {
    private String constraintName = null;

    /**
     * @param constraintName
     * @param child
     * @param parent
     */
    TableConstraint (String constaraintName, TableFKeys child, TableFKeys parent) {
      super ();
      this.constraintName = constaraintName;
      this.child = child;
      this.parent = parent;
    }

    private TableFKeys child  = null;
    private TableFKeys parent = null;

    /**
     * @return the constraintName
     */
    public String getConstaraintName () {
      return constraintName;
    }

    /**
     * @param constraintName
     *          the constraintName to set
     */
    public void setConstaraintName (String constaraintName) {
      this.constraintName = constaraintName;
    }

    /**
     * @return the child
     */
    public TableFKeys getChild () {
      return child;
    }

    /**
     * @param child
     *          the child to set
     */
    public void setChild (TableFKeys child) {
      this.child = child;
    }

    /**
     * @return the parent
     */
    public TableFKeys getParent () {
      return parent;
    }

    /**
     * @param parent
     *          the parent to set
     */
    public void setParent (TableFKeys parent) {
      this.parent = parent;
    }

    /**
     * @return the child
     */
    public String getChildName () {
      return child.getTablename ();
    }

    /**
     * @return the parent
     */
    public String getParentName () {
      return parent.getTablename ();
    }

  }

  class TableFKeys
  {
    private String       tablename = null;
    private List<String> fkeys     = new ArrayList<String> (); // order!!!

    TableFKeys (String tblname, List<String> fks)
    {
      this.tablename = tblname;
      this.fkeys = fks;
    }

    /**
     * @return the tablename
     */
    public String getTablename () {
      return tablename;
    }

    /**
     * @param tablename
     *          the tablename to set
     */
    public void setTablename (String tablename) {
      this.tablename = tablename;
    }

    /**
     * @return the fkeys
     */
    public List<String> getFkeys () {
      return fkeys;
    }

    /**
     * @param fkeys
     *          the fkeys to set
     */
    public void setFkeys (List<String> fkeys) {
      this.fkeys = fkeys;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode () {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType ().hashCode ();
      result = prime * result + ((fkeys == null) ? 0 : fkeys.hashCode ());
      result = prime * result + ((tablename == null) ? 0 : tablename.hashCode ());
      return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals (Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass () != obj.getClass ())
        return false;
      TableFKeys other = (TableFKeys) obj;
      if (!getOuterType ().equals (other.getOuterType ()))
        return false;
      if (fkeys == null) {
        if (other.fkeys != null)
          return false;
      } else if (!fkeys.equals (other.fkeys))
        return false;
      if (tablename == null) {
        if (other.tablename != null)
          return false;
      } else if (!tablename.equals (other.tablename))
        return false;
      return true;
    }

    // boolean equals (TableFKeys rh)
    // {
    // boolean res = true;
    // for (String key : this.fkeys)
    // {
    // res &= (key.equals (rh.getFkeys ().get (this.fkeys.indexOf (key))));
    // }
    // return (this.tablename.endsWith (rh.tablename))
    // && res;
    // }

    private UnitTestFlag getOuterType () {
      return UnitTestFlag.this;
    }
  }
}
