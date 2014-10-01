/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.common.rtspackage.proctor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

import tds.dll.common.rtspackage.IRtsPackageReader;
import tds.dll.common.rtspackage.common.exception.RtsPackageReaderException;
import tds.dll.common.rtspackage.common.table.RtsRecord;
import tds.dll.common.rtspackage.common.table.RtsTable;
import tds.dll.common.rtspackage.proctor.data.Proctor;
import tds.dll.common.rtspackage.proctor.data.ProctorPackage;
import tds.dll.common.rtspackage.proctor.data.Test;
import tds.dll.common.rtspackage.proctor.data.Tests;

/**
 * @author jmambo
 * 
 */
public class ProctorPackageReader implements IRtsPackageReader
{

  private Proctor        _proctor;
  private ProctorPackage _proctorPackage;

  @Override
  public String getFieldValue (String fieldName) {
    String value = null;
    switch (fieldName) {
    case "NumberOfTests":
      value = String.valueOf (getNumberOfTests ());
      break;
    default:
      break;
    }
    return value;
  }

  @Override
  public RtsRecord getRtsRecord (String fieldName) {
    return null;
  }

  @Override
  public RtsTable getRtsTable (String fieldName) {
    if (fieldName.equals ("Tests")) {
      return getTests ();
    }
    return null;
  }

  @Override
  public <T> T getPackage (Class<T> clazz) {
    return clazz.cast (_proctorPackage);
  }

  @Override
  public boolean read (byte[] pkg) throws RtsPackageReaderException {
    if (pkg == null) {
      throw new RtsPackageReaderException ("Package object is null");
    }
    try (ObjectInputStream objectIn = new ObjectInputStream (new GZIPInputStream (new ByteArrayInputStream (pkg)))) {
      _proctorPackage = (ProctorPackage) objectIn.readObject ();
      _proctor = _proctorPackage.getProctor ();
      return true;
    } catch (ClassNotFoundException | IOException e) {
      throw new RtsPackageReaderException (e.getMessage (), e);
    }
  }
  
  private RtsTable getTests () {
    Tests tests = _proctor.getTests ();
    RtsTable rtsTable = new RtsTable();
    for (Test test : tests.getTest ()) {
      rtsTable.addRecord (new RtsRecord (new String[][] {
          {"TestID", test.getTestName ()},   //TODO: jmambo use TestId()
          {"Subject", test.getSubjectCode ()},
          {"TestKey", test.getTestName ()},  //TODO: jmambo use TestId()
          {"TestType", test.getType ()}
      }));
    }
    return rtsTable;
  }

  private int getNumberOfTests () {
    return _proctor.getTests ().getTest ().size ();
  }

}
