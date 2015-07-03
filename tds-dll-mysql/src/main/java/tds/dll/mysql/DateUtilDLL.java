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

import AIR.Common.DB.AbstractConnectionManager;
import AIR.Common.DB.DATABASE_TYPE;
import AIR.Common.Sql.AbstractDateUtilDll;
import TDS.Shared.Data.ColumnResultSet;
import TDS.Shared.Exceptions.ReturnStatusException;

public class DateUtilDLL extends AbstractDateUtilDll
{
  private static Logger _logger = LoggerFactory.getLogger (DateUtilDLL.class);

  @Autowired
  private AbstractConnectionManager _connectionManager = null;
  
  public Date getDate (Connection connection) throws SQLException {
    DATABASE_TYPE dbType = _connectionManager.getDatabaseDialect ();
    Timestamp timestamp;
    String cmd = null;
    switch(dbType) {
      case MYSQL:
        cmd = "select now(3)";
        break;
      case  SQLSERVER:
        cmd = "select getdate()";
        break;
      default:
        cmd = "select now(3)";
        break;
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
        return _now;
      } else {
        throw new SQLException ("Failed  get current date!");
      }

    } catch (SQLException exp) {
      _logger.error (exp.getMessage (),exp);
      throw exp;
    }
  }
  public Date getDateWRetStatus (Connection connection) throws ReturnStatusException {
    try {
      return getDate(connection);
    } catch (SQLException se) {
      throw new ReturnStatusException (se);
    }
  }
}
