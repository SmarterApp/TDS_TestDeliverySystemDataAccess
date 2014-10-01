/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.common.rtspackage.student;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.zip.GZIPOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tds.dll.common.rtspackage.IRtsPackageWriter;
import tds.dll.common.rtspackage.common.exception.RtsPackageWriterException;
import tds.dll.common.rtspackage.student.data.StudentPackage;

/**
 * @author jmambo
 * 
 */
public class StudentPackageWriter implements IRtsPackageWriter<StudentPackage>
{

  private static final Logger _logger = LoggerFactory.getLogger (StudentPackageWriter.class);

  private static JAXBContext  _jaxbContext = getJaxbContext();

  private StudentPackage      _studentPackage;


  @Override
  public void writeObject (String pkg) throws RtsPackageWriterException {
    try {
      Unmarshaller jaxbUnmarshaller = _jaxbContext.createUnmarshaller ();
      _studentPackage = (StudentPackage) jaxbUnmarshaller.unmarshal (new StringReader (pkg));
    } catch (JAXBException e) {
      _logger.debug ("Student Package: {}",  pkg );
      throw new RtsPackageWriterException (e.getMessage (), e);
    }
  }


  @Override
  public StudentPackage getObject () {
    return _studentPackage;
  }


  @Override
  public InputStream getInputStream () throws RtsPackageWriterException {
    byte[] byteArray;
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream ();
        GZIPOutputStream gzos = new GZIPOutputStream (baos);
        ObjectOutputStream oos = new ObjectOutputStream (gzos)) {
      oos.writeObject (_studentPackage);
      oos.flush ();
      gzos.finish ();
      gzos.flush ();
      byteArray = baos.toByteArray ();
    } catch (IOException e) {
      e.printStackTrace ();
      throw new RtsPackageWriterException (e.getMessage (), e);
    }
    return new ByteArrayInputStream (byteArray);
  }

  private static JAXBContext getJaxbContext() {
      try {
        return JAXBContext.newInstance (StudentPackage.class);
      } catch (JAXBException e) {
        _logger.error (e.getMessage (), e);
      }
      return null;
   }
  
}
