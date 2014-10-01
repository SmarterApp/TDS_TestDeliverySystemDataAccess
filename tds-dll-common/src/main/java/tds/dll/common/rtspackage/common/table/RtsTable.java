/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.common.rtspackage.common.table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jmambo
 *
 */
public class RtsTable
{
  
  List<RtsRecord> _records = new ArrayList<RtsRecord>();
  
  public void addRecord(RtsRecord rtsRecord) {
      _records.add(rtsRecord);
  }
  
  public List<RtsRecord> getRecords() {
    return _records;
  }

}
