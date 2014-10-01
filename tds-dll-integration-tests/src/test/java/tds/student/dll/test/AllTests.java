/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.student.dll.test;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author akulakov
 *
 */
@Ignore
@RunWith (Suite.class)
@SuiteClasses ({ TestCommonDLL.class, 
                  TestItemSelectionDLL.class, 
                  TestReportingDLL.class,
                  TestProctorDLL.class, 
                  TestStudentDLL.class })
public class AllTests
{

}
