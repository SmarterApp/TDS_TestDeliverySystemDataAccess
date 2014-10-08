/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import TDS.Shared.Data.ColumnResultSet;
import TDS.Shared.Exceptions.ReturnStatusException;
import AIR.Common.DB.AbstractConnectionManager;
import AIR.Common.DB.DATABASE_TYPE;
import AIR.Common.Sql.AbstractDateUtilDll;

public class DateUtilDLL extends AbstractDateUtilDll
{
  private static Logger _logger = LoggerFactory.getLogger (DateUtilDLL.class);

  @Autowired
  private AbstractConnectionManager _connectionManager = null;
  
  public Date getDate (Connection connection) throws SQLException {
    DATABASE_TYPE dbType = _connectionManager.getDatabaseDialect ();
    Timestamp timestamp;
    String cmd = null;
    if (dbType == DATABASE_TYPE.SQLSERVER) {
      cmd = "select getdate()";
    } else if (dbType == DATABASE_TYPE.MYSQL) {
      cmd = "select now(3)";
    }
    
    try (PreparedStatement stm = connection.prepareStatement (cmd)) {
      ColumnResultSet reader = null;

      boolean gotresult = stm.execute ();
      if (gotresult)
        reader = ColumnResultSet.getColumnResultSet (stm.getResultSet ());
      /* check if result set has any rows */
      if (reader != null && reader.next () == true) {
        timestamp = reader.getTimestamp (1);
        _now = new Date (timestamp.getTime ());
      } else {
        throw new SQLException ("Failed  get current date!");
      }

    } catch (SQLException exp) {
      _logger.error (exp.getMessage (),exp);
      throw exp;
    }
    return _now;
  }
  public Date getDateWRetStatus (Connection connection) throws ReturnStatusException {
  Date _now = null;
    try {
      _now= getDate(connection);
    } catch (SQLException se) {
      throw new ReturnStatusException (se);
    }
    return _now;
  }
}
