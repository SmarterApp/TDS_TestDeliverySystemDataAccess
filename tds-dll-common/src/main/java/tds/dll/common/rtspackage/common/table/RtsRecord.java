/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.common.rtspackage.common.table;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @author jmambo
 *
 */
public class RtsRecord 
{
  
  private Map<String, String> _fieldMap = new LinkedHashMap<>();
  private RtsField[] _rtsFields;

  public RtsRecord(String[][] fields) {
    for (String[] field  : fields) {
       _fieldMap.put(field[0], field[1]);
    }
    setFields();
  }
 
  public String get(String key) {
    return _fieldMap.get (key);
  }
  
  public RtsField[] getFields() {
    return _rtsFields;
  }
  
  public String getValue(String key) {
    return get (key);
  }
  
  public String[] getFieldNames() {
    return _fieldMap.keySet().toArray (new String[0]);
  }

  public Set<Entry<String, String>> getEntrySet() {
    return _fieldMap.entrySet();
  }
  
  public Set<String> getKeySet() {
     return _fieldMap.keySet();
  }
  
  
  private void setFields() {
    int i=0;
    _rtsFields = new RtsField[_fieldMap.size ()];
    for (Entry<String,String> entry :  _fieldMap.entrySet()) {
      _rtsFields[i++]= new RtsField(entry.getKey(), entry.getValue());
    }
  }

}
