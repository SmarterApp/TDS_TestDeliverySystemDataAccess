-- ----------------------------------------------------------------------------------------------------------------------
-- Description:  Load sample test items into the itembank database.  The XML data for this SQL came from here:
-- ftp://ftps.smarterbalanced.org/~sbacpublic/Public/PracticeAndTrainingTests/2015-08-28_TrainingTestPackagesAndContent.zip
-- The .zip stores the XML files shown below in Test Packages/Administration.  This SQL is executed during the
-- db-schema-setup.sh script.
--
-- Usage: Execute against the itembank database after the schema has been created.
-- ----------------------------------------------------------------------------------------------------------------------
USE itembank;
SET SQL_SAFE_UPDATES = 0;
CALL loader_main('<?xml version="1.0" encoding="utf-8"?>
<testspecification purpose="administration" publisher="SBAC_PT" publishdate="Aug 28 2015 12:52AM" version="1.0">
  <identifier uniqueid="(SBAC_PT)SBAC ELA 3-ELA-3-Spring-2014-2015" name="SBAC ELA 3-ELA-3" label="ELA Grades 3-5 Training Test" version="8226" />
  <property name="subject" value="ELA" label="ELA" />
  <property name="grade" value="3" label="grade 3" />
  <property name="grade" value="4" label="grade 4" />
  <property name="grade" value="5" label="grade 5" />
  <property name="type" value="summative" label="summative" />
  <administration>
    <testblueprint>
      <bpelement elementtype="test" minopitems="6" maxopitems="6" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="6">
        <identifier uniqueid="(SBAC_PT)SBAC ELA 3-ELA-3-Spring-2014-2015" name="SBAC ELA 3-ELA-3" version="8226" />
      </bpelement>
      <bpelement elementtype="segment" minopitems="6" maxopitems="6" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="6">
        <identifier uniqueid="(SBAC_PT)SBAC ELA 3-ELA-3-Spring-2014-2015" name="SBAC ELA 3-ELA-3" version="8226" />
      </bpelement>
      <bpelement elementtype="strand" minopitems="6" maxopitems="6" opitemcount="0" ftitemcount="6">
        <identifier uniqueid="SBAC_PT-ELA-Undesignated" name="ELA-Undesignated" version="8226" />
      </bpelement>
    </testblueprint>
    <poolproperty property="--ITEMTYPE--" value="EBSR" label="EBSR" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="ER" label="ER" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="2" />
    <poolproperty property="--ITEMTYPE--" value="SA" label="SA" itemcount="1" />
    <poolproperty property="Language" value="ENU" label="English" itemcount="6" />
    <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="6" />
    <itempool>
      <passage filename="stim-187-3709.xml">
        <identifier uniqueid="187-3709" version="8226" />
      </passage>
      <passage filename="stim-187-3716.xml">
        <identifier uniqueid="187-3716" version="8226" />
      </passage>
      <testitem filename="item-187-1690.xml" itemtype="MI">
        <identifier uniqueid="187-1690" version="8226" />
        <bpref>(SBAC_PT)SBAC ELA 3-ELA-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MI" label="MI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1692.xml" itemtype="SA">
        <identifier uniqueid="187-1692" version="8226" />
        <bpref>(SBAC_PT)SBAC ELA 3-ELA-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <passageref>187-3709</passageref>
        <poolproperty property="--ITEMTYPE--" value="SA" label="SA" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="2" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1700.xml" itemtype="EBSR">
        <identifier uniqueid="187-1700" version="8226" />
        <bpref>(SBAC_PT)SBAC ELA 3-ELA-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <passageref>187-3709</passageref>
        <poolproperty property="--ITEMTYPE--" value="EBSR" label="EBSR" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1701.xml" itemtype="ER">
        <identifier uniqueid="187-1701" version="8226" />
        <bpref>(SBAC_PT)SBAC ELA 3-ELA-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="ER" label="ER" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="2" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1703.xml" itemtype="MS">
        <identifier uniqueid="187-1703" version="8226" />
        <bpref>(SBAC_PT)SBAC ELA 3-ELA-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MS" label="MS" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1728.xml" itemtype="MS">
        <identifier uniqueid="187-1728" version="8226" />
        <bpref>(SBAC_PT)SBAC ELA 3-ELA-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <passageref>187-3716</passageref>
        <poolproperty property="--ITEMTYPE--" value="MS" label="MS" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
    </itempool>
    <testform length="6">
      <identifier uniqueid="(SBAC_PT)SBAC ELA 3-ELA-3-Spring-2014-2015:Default-ENU" name="(SBAC_PT)SBAC ELA 3-ELA-3-Spring-2014-2015:Default-ENU" version="8226" />
      <property name="language" value="ENU" label="English" />
      <poolproperty property="--ITEMTYPE--" value="EBSR" label="EBSR" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="ER" label="ER" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="2" />
      <poolproperty property="--ITEMTYPE--" value="SA" label="SA" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="6" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="6" />
      <formpartition>
        <identifier uniqueid="187-695" name="ELA Training 3-5::SP15" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-695:G-187-3709-0" name="187-695:G-187-3709-0" version="8226" />
          <passageref>187-3709</passageref>
          <groupitem itemid="187-1692" formposition="1" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
          <groupitem itemid="187-1700" formposition="2" groupposition="2" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-695:I-187-1701" name="187-695:I-187-1701" version="8226" />
          <groupitem itemid="187-1701" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-695:I-187-1703" name="187-695:I-187-1703" version="8226" />
          <groupitem itemid="187-1703" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-695:G-187-3716-0" name="187-695:G-187-3716-0" version="8226" />
          <passageref>187-3716</passageref>
          <groupitem itemid="187-1728" formposition="5" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-695:I-187-1690" name="187-695:I-187-1690" version="8226" />
          <groupitem itemid="187-1690" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <testform length="6">
      <identifier uniqueid="(SBAC_PT)SBAC ELA 3-ELA-3-Spring-2014-2015:Default-ENU-Braille" name="(SBAC_PT)SBAC ELA 3-ELA-3-Spring-2014-2015:Default-ENU-Braille" version="8226" />
      <property name="language" value="ENU-Braille" label="English" />
      <poolproperty property="--ITEMTYPE--" value="EBSR" label="EBSR" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="ER" label="ER" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="2" />
      <poolproperty property="--ITEMTYPE--" value="SA" label="SA" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="6" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="6" />
      <formpartition>
        <identifier uniqueid="187-699" name="ELA Training 3-5::SP15::BRL" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-699:G-187-3709-0" name="187-699:G-187-3709-0" version="8226" />
          <passageref>187-3709</passageref>
          <groupitem itemid="187-1692" formposition="1" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
          <groupitem itemid="187-1700" formposition="2" groupposition="2" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-699:I-187-1701" name="187-699:I-187-1701" version="8226" />
          <groupitem itemid="187-1701" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-699:I-187-1703" name="187-699:I-187-1703" version="8226" />
          <groupitem itemid="187-1703" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-699:G-187-3716-0" name="187-699:G-187-3716-0" version="8226" />
          <passageref>187-3716</passageref>
          <groupitem itemid="187-1728" formposition="5" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-699:I-187-1690" name="187-699:I-187-1690" version="8226" />
          <groupitem itemid="187-1690" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <adminsegment segmentid="(SBAC_PT)SBAC ELA 3-ELA-3-Spring-2014-2015" position="1" itemselection="fixedform">
      <segmentblueprint>
        <segmentbpelement bpelementid="SBAC_PT-ELA-Undesignated" minopitems="6" maxopitems="6" />
      </segmentblueprint>
      <itemselector type="fixedform">
        <identifier uniqueid="AIR FIXEDFORM1" name="AIR FIXEDFORM" label="AIR FIXEDFORM" version="1.0" />
        <itemselectionparameter bpelementid="(SBAC_PT)SBAC ELA 3-ELA-3-Spring-2014-2015">
          <property name="slope" value="1" label="slope" />
          <property name="intercept" value="1" label="intercept" />
        </itemselectionparameter>
      </itemselector>
      <segmentform formpartitionid="187-695" />
      <segmentform formpartitionid="187-699" />
    </adminsegment>
  </administration>
</testspecification>');

CALL loader_main('<?xml version="1.0" encoding="utf-8"?>
<testspecification purpose="administration" publisher="SBAC_PT" publishdate="Aug 28 2015 12:52AM" version="1.0">
  <identifier uniqueid="(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015" name="SBAC ELA 6-ELA-6" label="ELA Grades 6-8 Training Test " version="8226" />
  <property name="subject" value="ELA" label="ELA" />
  <property name="grade" value="6" label="grade 6" />
  <property name="grade" value="7" label="grade 7" />
  <property name="grade" value="8" label="grade 8" />
  <property name="type" value="summative" label="summative" />
  <administration>
    <testblueprint>
      <bpelement elementtype="test" minopitems="7" maxopitems="7" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="7">
        <identifier uniqueid="(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015" name="SBAC ELA 6-ELA-6" version="8226" />
      </bpelement>
      <bpelement elementtype="segment" minopitems="7" maxopitems="7" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="7">
        <identifier uniqueid="(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015" name="SBAC ELA 6-ELA-6" version="8226" />
      </bpelement>
      <bpelement elementtype="strand" minopitems="7" maxopitems="7" opitemcount="0" ftitemcount="7">
        <identifier uniqueid="SBAC_PT-ELA-Undesignated" name="ELA-Undesignated" version="8226" />
      </bpelement>
    </testblueprint>
    <poolproperty property="--ITEMTYPE--" value="EBSR" label="EBSR" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="ER" label="ER" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="HTQ" label="HTQ" itemcount="2" />
    <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
    <poolproperty property="Language" value="ENU" label="English" itemcount="7" />
    <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="7" />
    <itempool>
      <passage filename="stim-187-3717.xml">
        <identifier uniqueid="187-3717" version="8226" />
      </passage>
      <passage filename="stim-187-3728.xml">
        <identifier uniqueid="187-3728" version="8233" />
      </passage>
      <passage filename="stim-187-3740.xml">
        <identifier uniqueid="187-3740" version="8233" />
      </passage>
      <passage filename="stim-187-3747.xml">
        <identifier uniqueid="187-3747" version="8233" />
      </passage>
      <testitem filename="item-187-1702.xml" itemtype="ER">
        <identifier uniqueid="187-1702" version="8226" />
        <bpref>(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="ER" label="ER" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="2" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1729.xml" itemtype="EBSR">
        <identifier uniqueid="187-1729" version="8226" />
        <bpref>(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <passageref>187-3717</passageref>
        <poolproperty property="--ITEMTYPE--" value="EBSR" label="EBSR" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-2128.xml" itemtype="MI">
        <identifier uniqueid="187-2128" version="8233" />
        <bpref>(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <passageref>187-3728</passageref>
        <poolproperty property="--ITEMTYPE--" value="MI" label="MI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-2527.xml" itemtype="MS">
        <identifier uniqueid="187-2527" version="8233" />
        <bpref>(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <passageref>187-3740</passageref>
        <poolproperty property="--ITEMTYPE--" value="MS" label="MS" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-2611.xml" itemtype="HTQ">
        <identifier uniqueid="187-2611" version="8233" />
        <bpref>(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <passageref>187-3747</passageref>
        <poolproperty property="--ITEMTYPE--" value="HTQ" label="HTQ" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-2620.xml" itemtype="HTQ">
        <identifier uniqueid="187-2620" version="8233" />
        <bpref>(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <passageref>187-3747</passageref>
        <poolproperty property="--ITEMTYPE--" value="HTQ" label="HTQ" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-946.xml" itemtype="MC">
        <identifier uniqueid="187-946" version="8226" />
        <bpref>(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MC" label="MC" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
    </itempool>
    <testform length="7">
      <identifier uniqueid="(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015:Default-ENU" name="(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015:Default-ENU" version="8226" />
      <property name="language" value="ENU" label="English" />
      <poolproperty property="--ITEMTYPE--" value="EBSR" label="EBSR" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="ER" label="ER" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="HTQ" label="HTQ" itemcount="2" />
      <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="7" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="7" />
      <formpartition>
        <identifier uniqueid="187-696" name="ELA Training 6-8::SP15" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-696:I-187-1702" name="187-696:I-187-1702" version="8226" />
          <groupitem itemid="187-1702" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-696:G-187-3717-0" name="187-696:G-187-3717-0" version="8226" />
          <passageref>187-3717</passageref>
          <groupitem itemid="187-1729" formposition="2" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-696:I-187-946" name="187-696:I-187-946" version="8226" />
          <groupitem itemid="187-946" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-696:G-187-3740-0" name="187-696:G-187-3740-0" version="8226" />
          <passageref>187-3740</passageref>
          <groupitem itemid="187-2527" formposition="4" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-696:G-187-3747-0" name="187-696:G-187-3747-0" version="8226" />
          <passageref>187-3747</passageref>
          <groupitem itemid="187-2620" formposition="5" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
          <groupitem itemid="187-2611" formposition="6" groupposition="2" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="6" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-696:G-187-3728-0" name="187-696:G-187-3728-0" version="8226" />
          <passageref>187-3728</passageref>
          <groupitem itemid="187-2128" formposition="7" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <testform length="7">
      <identifier uniqueid="(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015:Default-ENU-Braille" name="(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015:Default-ENU-Braille" version="8226" />
      <property name="language" value="ENU-Braille" label="English" />
      <poolproperty property="--ITEMTYPE--" value="EBSR" label="EBSR" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="ER" label="ER" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="HTQ" label="HTQ" itemcount="2" />
      <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="7" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="7" />
      <formpartition>
        <identifier uniqueid="187-700" name="ELA Training 6-8::SP15::BRL" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-700:I-187-1702" name="187-700:I-187-1702" version="8226" />
          <groupitem itemid="187-1702" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-700:G-187-3717-0" name="187-700:G-187-3717-0" version="8226" />
          <passageref>187-3717</passageref>
          <groupitem itemid="187-1729" formposition="2" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-700:I-187-946" name="187-700:I-187-946" version="8226" />
          <groupitem itemid="187-946" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-700:G-187-3740-0" name="187-700:G-187-3740-0" version="8226" />
          <passageref>187-3740</passageref>
          <groupitem itemid="187-2527" formposition="4" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-700:G-187-3747-0" name="187-700:G-187-3747-0" version="8226" />
          <passageref>187-3747</passageref>
          <groupitem itemid="187-2620" formposition="5" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
          <groupitem itemid="187-2611" formposition="6" groupposition="2" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="6" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-700:G-187-3728-0" name="187-700:G-187-3728-0" version="8226" />
          <passageref>187-3728</passageref>
          <groupitem itemid="187-2128" formposition="7" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <adminsegment segmentid="(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015" position="1" itemselection="fixedform">
      <segmentblueprint>
        <segmentbpelement bpelementid="SBAC_PT-ELA-Undesignated" minopitems="7" maxopitems="7" />
      </segmentblueprint>
      <itemselector type="fixedform">
        <identifier uniqueid="AIR FIXEDFORM1" name="AIR FIXEDFORM" label="AIR FIXEDFORM" version="1.0" />
        <itemselectionparameter bpelementid="(SBAC_PT)SBAC ELA 6-ELA-6-Spring-2014-2015">
          <property name="slope" value="1" label="slope" />
          <property name="intercept" value="1" label="intercept" />
        </itemselectionparameter>
      </itemselector>
      <segmentform formpartitionid="187-696" />
      <segmentform formpartitionid="187-700" />
    </adminsegment>
  </administration>
</testspecification>');

CALL loader_main('<?xml version="1.0" encoding="utf-8"?>
<testspecification purpose="administration" publisher="SBAC_PT" publishdate="Aug 28 2015 12:52AM" version="1.0">
  <identifier uniqueid="(SBAC_PT)SBAC ELA HS-ELA-10-Spring-2014-2015" name="SBAC ELA HS-ELA-10" label="ELA HS Training Test " version="8226" />
  <property name="subject" value="ELA" label="ELA" />
  <property name="grade" value="10" label="grade 10" />
  <property name="grade" value="11" label="grade 11" />
  <property name="grade" value="12" label="grade 12" />
  <property name="grade" value="9" label="grade 9" />
  <property name="type" value="summative" label="summative" />
  <administration>
    <testblueprint>
      <bpelement elementtype="test" minopitems="6" maxopitems="6" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="6">
        <identifier uniqueid="(SBAC_PT)SBAC ELA HS-ELA-10-Spring-2014-2015" name="SBAC ELA HS-ELA-10" version="8226" />
      </bpelement>
      <bpelement elementtype="segment" minopitems="6" maxopitems="6" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="6">
        <identifier uniqueid="(SBAC_PT)SBAC ELA HS-ELA-10-Spring-2014-2015" name="SBAC ELA HS-ELA-10" version="8226" />
      </bpelement>
      <bpelement elementtype="strand" minopitems="6" maxopitems="6" opitemcount="0" ftitemcount="6">
        <identifier uniqueid="SBAC_PT-ELA-Undesignated" name="ELA-Undesignated" version="8226" />
      </bpelement>
    </testblueprint>
    <poolproperty property="--ITEMTYPE--" value="EBSR" label="EBSR" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="ER" label="ER" itemcount="2" />
    <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="2" />
    <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
    <poolproperty property="Language" value="ENU" label="English" itemcount="6" />
    <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="6" />
    <itempool>
      <passage filename="stim-187-3712.xml">
        <identifier uniqueid="187-3712" version="8226" />
      </passage>
      <passage filename="stim-187-3713.xml">
        <identifier uniqueid="187-3713" version="8226" />
      </passage>
      <testitem filename="item-187-1689.xml" itemtype="MI">
        <identifier uniqueid="187-1689" version="8226" />
        <bpref>(SBAC_PT)SBAC ELA HS-ELA-10-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MI" label="MI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1695.xml" itemtype="EBSR">
        <identifier uniqueid="187-1695" version="8226" />
        <bpref>(SBAC_PT)SBAC ELA HS-ELA-10-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <passageref>187-3712</passageref>
        <poolproperty property="--ITEMTYPE--" value="EBSR" label="EBSR" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1696.xml" itemtype="MC">
        <identifier uniqueid="187-1696" version="8226" />
        <bpref>(SBAC_PT)SBAC ELA HS-ELA-10-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <passageref>187-3713</passageref>
        <poolproperty property="--ITEMTYPE--" value="MC" label="MC" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1699.xml" itemtype="ER">
        <identifier uniqueid="187-1699" version="8226" />
        <bpref>(SBAC_PT)SBAC ELA HS-ELA-10-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="ER" label="ER" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="2" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1704.xml" itemtype="MC">
        <identifier uniqueid="187-1704" version="8226" />
        <bpref>(SBAC_PT)SBAC ELA HS-ELA-10-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MC" label="MC" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1705.xml" itemtype="ER">
        <identifier uniqueid="187-1705" version="8226" />
        <bpref>(SBAC_PT)SBAC ELA HS-ELA-10-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-ELA-Undesignated</bpref>
        <passageref>187-3712</passageref>
        <poolproperty property="--ITEMTYPE--" value="ER" label="ER" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="2" weight="1.000000000000000e+000" />
      </testitem>
    </itempool>
    <testform length="6">
      <identifier uniqueid="(SBAC_PT)SBAC ELA HS-ELA-10-Spring-2014-2015:Default-ENU" name="(SBAC_PT)SBAC ELA HS-ELA-10-Spring-2014-2015:Default-ENU" version="8226" />
      <property name="language" value="ENU" label="English" />
      <poolproperty property="--ITEMTYPE--" value="EBSR" label="EBSR" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="ER" label="ER" itemcount="2" />
      <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="2" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="6" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="6" />
      <formpartition>
        <identifier uniqueid="187-697" name="ELA Training HS::SP15" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-697:G-187-3712-0" name="187-697:G-187-3712-0" version="8226" />
          <passageref>187-3712</passageref>
          <groupitem itemid="187-1705" formposition="1" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
          <groupitem itemid="187-1695" formposition="2" groupposition="2" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-697:I-187-1699" name="187-697:I-187-1699" version="8226" />
          <groupitem itemid="187-1699" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-697:I-187-1704" name="187-697:I-187-1704" version="8226" />
          <groupitem itemid="187-1704" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-697:G-187-3713-0" name="187-697:G-187-3713-0" version="8226" />
          <passageref>187-3713</passageref>
          <groupitem itemid="187-1696" formposition="5" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-697:I-187-1689" name="187-697:I-187-1689" version="8226" />
          <groupitem itemid="187-1689" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <testform length="6">
      <identifier uniqueid="(SBAC_PT)SBAC ELA HS-ELA-10-Spring-2014-2015:Default-ENU-Braille" name="(SBAC_PT)SBAC ELA HS-ELA-10-Spring-2014-2015:Default-ENU-Braille" version="8226" />
      <property name="language" value="ENU-Braille" label="English" />
      <poolproperty property="--ITEMTYPE--" value="EBSR" label="EBSR" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="ER" label="ER" itemcount="2" />
      <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="2" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="6" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="6" />
      <formpartition>
        <identifier uniqueid="187-701" name="ELA Training HS::SP15::BRL" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-701:G-187-3712-0" name="187-701:G-187-3712-0" version="8226" />
          <passageref>187-3712</passageref>
          <groupitem itemid="187-1705" formposition="1" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
          <groupitem itemid="187-1695" formposition="2" groupposition="2" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-701:I-187-1699" name="187-701:I-187-1699" version="8226" />
          <groupitem itemid="187-1699" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-701:I-187-1704" name="187-701:I-187-1704" version="8226" />
          <groupitem itemid="187-1704" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-701:G-187-3713-0" name="187-701:G-187-3713-0" version="8226" />
          <passageref>187-3713</passageref>
          <groupitem itemid="187-1696" formposition="5" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-701:I-187-1689" name="187-701:I-187-1689" version="8226" />
          <groupitem itemid="187-1689" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <adminsegment segmentid="(SBAC_PT)SBAC ELA HS-ELA-10-Spring-2014-2015" position="1" itemselection="fixedform">
      <segmentblueprint>
        <segmentbpelement bpelementid="SBAC_PT-ELA-Undesignated" minopitems="6" maxopitems="6" />
      </segmentblueprint>
      <itemselector type="fixedform">
        <identifier uniqueid="AIR FIXEDFORM1" name="AIR FIXEDFORM" label="AIR FIXEDFORM" version="1.0" />
        <itemselectionparameter bpelementid="(SBAC_PT)SBAC ELA HS-ELA-10-Spring-2014-2015">
          <property name="slope" value="1" label="slope" />
          <property name="intercept" value="1" label="intercept" />
        </itemselectionparameter>
      </itemselector>
      <segmentform formpartitionid="187-697" />
      <segmentform formpartitionid="187-701" />
    </adminsegment>
  </administration>
</testspecification>');

CALL loader_main('<?xml version="1.0" encoding="utf-8"?>
<testspecification purpose="administration" publisher="SBAC_PT" publishdate="Aug 28 2015 12:52AM" version="1.0">
  <identifier uniqueid="(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015" name="SBAC Math 3-MATH-3" label="Math Grades 3-5 Training Test " version="8226" />
  <property name="subject" value="MATH" label="MATH" />
  <property name="grade" value="3" label="grade 3" />
  <property name="grade" value="4" label="grade 4" />
  <property name="grade" value="5" label="grade 5" />
  <property name="type" value="summative" label="summative" />
  <administration>
    <testblueprint>
      <bpelement elementtype="test" minopitems="9" maxopitems="9" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="12">
        <identifier uniqueid="(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015" name="SBAC Math 3-MATH-3" version="8226" />
      </bpelement>
      <bpelement elementtype="segment" minopitems="9" maxopitems="9" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="12">
        <identifier uniqueid="(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015" name="SBAC Math 3-MATH-3" version="8226" />
      </bpelement>
      <bpelement elementtype="strand" minopitems="9" maxopitems="9" opitemcount="0" ftitemcount="12">
        <identifier uniqueid="SBAC_PT-MA-Undesignated" name="MA-Undesignated" version="8226" />
      </bpelement>
    </testblueprint>
    <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="2" />
    <poolproperty property="--ITEMTYPE--" value="GI" label="GI" itemcount="3" />
    <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="3" />
    <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="NL" label="NL" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
    <poolproperty property="Language" value="ENU" label="English" itemcount="9" />
    <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="9" />
    <poolproperty property="Language" value="ESN" label="Spanish" itemcount="9" />
    <itempool>
      <passage filename="stim-187-3554.xml">
        <identifier uniqueid="187-3554" version="8226" />
      </passage>
      <testitem filename="item-187-1126.xml" itemtype="MC">
        <identifier uniqueid="187-1126" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MC" label="MC" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1127.xml" itemtype="MC">
        <identifier uniqueid="187-1127" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MC" label="MC" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1129.xml" itemtype="EQ">
        <identifier uniqueid="187-1129" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1674.xml" itemtype="TI">
        <identifier uniqueid="187-1674" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="TI" label="TI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1684.xml" itemtype="MI">
        <identifier uniqueid="187-1684" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MI" label="MI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="2" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-540.xml" itemtype="GI">
        <identifier uniqueid="187-540" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="GI" label="GI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-543.xml" itemtype="MC">
        <identifier uniqueid="187-543" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MC" label="MC" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-545.xml" itemtype="NL">
        <identifier uniqueid="187-545" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <passageref>187-3554</passageref>
        <poolproperty property="--ITEMTYPE--" value="NL" label="NL" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-554.xml" itemtype="GI">
        <identifier uniqueid="187-554" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="GI" label="GI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="3" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-557.xml" itemtype="GI">
        <identifier uniqueid="187-557" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="GI" label="GI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-560.xml" itemtype="MS">
        <identifier uniqueid="187-560" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MS" label="MS" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-566.xml" itemtype="EQ">
        <identifier uniqueid="187-566" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
    </itempool>
    <testform length="9">
      <identifier uniqueid="(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015:Default-ENU" name="(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015:Default-ENU" version="8226" />
      <property name="language" value="ENU" label="English" />
      <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="GI" label="GI" itemcount="3" />
      <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="NL" label="NL" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="9" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="6" />
      <poolproperty property="Language" value="ESN" label="Spanish" itemcount="9" />
      <formpartition>
        <identifier uniqueid="187-627" name="Math Training 3-5::SP 15" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-627:I-187-543" name="187-627:I-187-543" version="8226" />
          <groupitem itemid="187-543" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-627:I-187-566" name="187-627:I-187-566" version="8226" />
          <groupitem itemid="187-566" formposition="2" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-627:I-187-540" name="187-627:I-187-540" version="8226" />
          <groupitem itemid="187-540" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-627:I-187-1684" name="187-627:I-187-1684" version="8226" />
          <groupitem itemid="187-1684" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-627:I-187-557" name="187-627:I-187-557" version="8226" />
          <groupitem itemid="187-557" formposition="5" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="6" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-627:I-187-560" name="187-627:I-187-560" version="8226" />
          <groupitem itemid="187-560" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="7" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-627:I-187-554" name="187-627:I-187-554" version="8226" />
          <groupitem itemid="187-554" formposition="7" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="8" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-627:G-187-3554-0" name="187-627:G-187-3554-0" version="8226" />
          <passageref>187-3554</passageref>
          <groupitem itemid="187-545" formposition="8" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="9" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-627:I-187-1674" name="187-627:I-187-1674" version="8226" />
          <groupitem itemid="187-1674" formposition="9" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <testform length="9">
      <identifier uniqueid="(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015:Default-ENU-Braille" name="(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015:Default-ENU-Braille" version="8226" />
      <property name="language" value="ENU-Braille" label="English" />
      <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="2" />
      <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="3" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="NL" label="NL" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="6" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="9" />
      <poolproperty property="Language" value="ESN" label="Spanish" itemcount="6" />
      <formpartition>
        <identifier uniqueid="187-628" name="Math Training 3-5::SP 15::Braille" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-628:I-187-543" name="187-628:I-187-543" version="8226" />
          <groupitem itemid="187-543" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-628:I-187-566" name="187-628:I-187-566" version="8226" />
          <groupitem itemid="187-566" formposition="2" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-628:I-187-1126" name="187-628:I-187-1126" version="8226" />
          <groupitem itemid="187-1126" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-628:I-187-1684" name="187-628:I-187-1684" version="8226" />
          <groupitem itemid="187-1684" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-628:I-187-1127" name="187-628:I-187-1127" version="8226" />
          <groupitem itemid="187-1127" formposition="5" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="6" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-628:I-187-560" name="187-628:I-187-560" version="8226" />
          <groupitem itemid="187-560" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="7" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-628:I-187-1129" name="187-628:I-187-1129" version="8226" />
          <groupitem itemid="187-1129" formposition="7" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="8" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-628:G-187-3554-0" name="187-628:G-187-3554-0" version="8226" />
          <passageref>187-3554</passageref>
          <groupitem itemid="187-545" formposition="8" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="9" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-628:I-187-1674" name="187-628:I-187-1674" version="8226" />
          <groupitem itemid="187-1674" formposition="9" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <testform length="9">
      <identifier uniqueid="(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015:Default-ESN" name="(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015:Default-ESN" version="8226" />
      <property name="language" value="ESN" label="Spanish" />
      <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="GI" label="GI" itemcount="3" />
      <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="NL" label="NL" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="9" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="6" />
      <poolproperty property="Language" value="ESN" label="Spanish" itemcount="9" />
      <formpartition>
        <identifier uniqueid="187-629" name="Math Training 3-5::SP 15::Spanish" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-629:I-187-543" name="187-629:I-187-543" version="8226" />
          <groupitem itemid="187-543" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-629:I-187-566" name="187-629:I-187-566" version="8226" />
          <groupitem itemid="187-566" formposition="2" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-629:I-187-540" name="187-629:I-187-540" version="8226" />
          <groupitem itemid="187-540" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-629:I-187-1684" name="187-629:I-187-1684" version="8226" />
          <groupitem itemid="187-1684" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-629:I-187-557" name="187-629:I-187-557" version="8226" />
          <groupitem itemid="187-557" formposition="5" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="6" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-629:I-187-560" name="187-629:I-187-560" version="8226" />
          <groupitem itemid="187-560" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="7" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-629:I-187-554" name="187-629:I-187-554" version="8226" />
          <groupitem itemid="187-554" formposition="7" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="8" maxitems="ALL" maxresponses="ALL">
          <identifier uniqueid="187-629:G-187-3554-0" name="187-629:G-187-3554-0" version="8226" />
          <passageref>187-3554</passageref>
          <groupitem itemid="187-545" formposition="8" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="9" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-629:I-187-1674" name="187-629:I-187-1674" version="8226" />
          <groupitem itemid="187-1674" formposition="9" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <adminsegment segmentid="(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015" position="1" itemselection="fixedform">
      <segmentblueprint>
        <segmentbpelement bpelementid="SBAC_PT-MA-Undesignated" minopitems="9" maxopitems="9" />
      </segmentblueprint>
      <itemselector type="fixedform">
        <identifier uniqueid="AIR FIXEDFORM1" name="AIR FIXEDFORM" label="AIR FIXEDFORM" version="1.0" />
        <itemselectionparameter bpelementid="(SBAC_PT)SBAC Math 3-MATH-3-Spring-2014-2015">
          <property name="slope" value="1" label="slope" />
          <property name="intercept" value="1" label="intercept" />
        </itemselectionparameter>
      </itemselector>
      <segmentform formpartitionid="187-627" />
      <segmentform formpartitionid="187-628" />
      <segmentform formpartitionid="187-629" />
    </adminsegment>
  </administration>
</testspecification>');

CALL loader_main('<?xml version="1.0" encoding="utf-8"?>
<testspecification purpose="administration" publisher="SBAC_PT" publishdate="Aug 28 2015 12:52AM" version="1.0">
  <identifier uniqueid="(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015" name="SBAC Math 6-MATH-6" label="Math Grades 6-8 Training Test" version="8226" />
  <property name="subject" value="MATH" label="MATH" />
  <property name="grade" value="6" label="grade 6" />
  <property name="type" value="summative" label="summative" />
  <administration>
    <testblueprint>
      <bpelement elementtype="test" minopitems="8" maxopitems="8" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="11">
        <identifier uniqueid="(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015" name="SBAC Math 6-MATH-6" version="8226" />
      </bpelement>
      <bpelement elementtype="segment" minopitems="8" maxopitems="8" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="11">
        <identifier uniqueid="(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015" name="SBAC Math 6-MATH-6" version="8226" />
      </bpelement>
      <bpelement elementtype="strand" minopitems="8" maxopitems="8" opitemcount="0" ftitemcount="11">
        <identifier uniqueid="SBAC_PT-MA-Undesignated" name="MA-Undesignated" version="8226" />
      </bpelement>
    </testblueprint>
    <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="3" />
    <poolproperty property="--ITEMTYPE--" value="GI" label="GI" itemcount="3" />
    <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="2" />
    <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
    <poolproperty property="Language" value="ENU" label="English" itemcount="8" />
    <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="8" />
    <poolproperty property="Language" value="ESN" label="Spanish" itemcount="8" />
    <itempool>
      <testitem filename="item-187-1383.xml" itemtype="EQ">
        <identifier uniqueid="187-1383" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1483.xml" itemtype="EQ">
        <identifier uniqueid="187-1483" version="8233" />
        <bpref>(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1485.xml" itemtype="MC">
        <identifier uniqueid="187-1485" version="8233" />
        <bpref>(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MC" label="MC" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1673.xml" itemtype="TI">
        <identifier uniqueid="187-1673" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="TI" label="TI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="2" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1675.xml" itemtype="MS">
        <identifier uniqueid="187-1675" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MS" label="MS" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1685.xml" itemtype="MI">
        <identifier uniqueid="187-1685" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MI" label="MI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-631.xml" itemtype="EQ">
        <identifier uniqueid="187-631" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-650.xml" itemtype="GI">
        <identifier uniqueid="187-650" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="GI" label="GI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="3" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-652.xml" itemtype="GI">
        <identifier uniqueid="187-652" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="GI" label="GI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-653.xml" itemtype="GI">
        <identifier uniqueid="187-653" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="GI" label="GI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-654.xml" itemtype="MC">
        <identifier uniqueid="187-654" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MC" label="MC" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
    </itempool>
    <testform length="8">
      <identifier uniqueid="(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015:Default-ENU" name="(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015:Default-ENU" version="8226" />
      <property name="language" value="ENU" label="English" />
      <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="GI" label="GI" itemcount="3" />
      <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="8" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="5" />
      <poolproperty property="Language" value="ESN" label="Spanish" itemcount="8" />
      <formpartition>
        <identifier uniqueid="187-692" name="Math Training 6::SP15" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-692:I-187-653" name="187-692:I-187-653" version="8226" />
          <groupitem itemid="187-653" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-692:I-187-631" name="187-692:I-187-631" version="8226" />
          <groupitem itemid="187-631" formposition="2" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-692:I-187-1673" name="187-692:I-187-1673" version="8226" />
          <groupitem itemid="187-1673" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-692:I-187-1675" name="187-692:I-187-1675" version="8226" />
          <groupitem itemid="187-1675" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-692:I-187-650" name="187-692:I-187-650" version="8226" />
          <groupitem itemid="187-650" formposition="5" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="6" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-692:I-187-1685" name="187-692:I-187-1685" version="8226" />
          <groupitem itemid="187-1685" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="7" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-692:I-187-654" name="187-692:I-187-654" version="8226" />
          <groupitem itemid="187-654" formposition="7" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="8" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-692:I-187-652" name="187-692:I-187-652" version="8226" />
          <groupitem itemid="187-652" formposition="8" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <testform length="8">
      <identifier uniqueid="(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015:Default-ENU-Braille" name="(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015:Default-ENU-Braille" version="8226" />
      <property name="language" value="ENU-Braille" label="English" />
      <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="3" />
      <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="2" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="5" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="8" />
      <poolproperty property="Language" value="ESN" label="Spanish" itemcount="5" />
      <formpartition>
        <identifier uniqueid="187-693" name="Math Training 6::SP15::Braille" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-693:I-187-1483" name="187-693:I-187-1483" version="8226" />
          <groupitem itemid="187-1483" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-693:I-187-631" name="187-693:I-187-631" version="8226" />
          <groupitem itemid="187-631" formposition="2" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-693:I-187-1673" name="187-693:I-187-1673" version="8226" />
          <groupitem itemid="187-1673" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-693:I-187-1675" name="187-693:I-187-1675" version="8226" />
          <groupitem itemid="187-1675" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-693:I-187-1485" name="187-693:I-187-1485" version="8226" />
          <groupitem itemid="187-1485" formposition="5" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="6" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-693:I-187-1685" name="187-693:I-187-1685" version="8226" />
          <groupitem itemid="187-1685" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="7" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-693:I-187-654" name="187-693:I-187-654" version="8226" />
          <groupitem itemid="187-654" formposition="7" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="8" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-693:I-187-1383" name="187-693:I-187-1383" version="8226" />
          <groupitem itemid="187-1383" formposition="8" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <testform length="8">
      <identifier uniqueid="(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015:Default-ESN" name="(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015:Default-ESN" version="8226" />
      <property name="language" value="ESN" label="Spanish" />
      <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="GI" label="GI" itemcount="3" />
      <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="8" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="5" />
      <poolproperty property="Language" value="ESN" label="Spanish" itemcount="8" />
      <formpartition>
        <identifier uniqueid="187-694" name="Math Training 6::SP15::Spanish" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-694:I-187-653" name="187-694:I-187-653" version="8226" />
          <groupitem itemid="187-653" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-694:I-187-631" name="187-694:I-187-631" version="8226" />
          <groupitem itemid="187-631" formposition="2" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-694:I-187-1673" name="187-694:I-187-1673" version="8226" />
          <groupitem itemid="187-1673" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-694:I-187-1675" name="187-694:I-187-1675" version="8226" />
          <groupitem itemid="187-1675" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-694:I-187-650" name="187-694:I-187-650" version="8226" />
          <groupitem itemid="187-650" formposition="5" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="6" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-694:I-187-1685" name="187-694:I-187-1685" version="8226" />
          <groupitem itemid="187-1685" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="7" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-694:I-187-654" name="187-694:I-187-654" version="8226" />
          <groupitem itemid="187-654" formposition="7" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="8" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-694:I-187-652" name="187-694:I-187-652" version="8226" />
          <groupitem itemid="187-652" formposition="8" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <adminsegment segmentid="(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015" position="1" itemselection="fixedform">
      <segmentblueprint>
        <segmentbpelement bpelementid="SBAC_PT-MA-Undesignated" minopitems="8" maxopitems="8" />
      </segmentblueprint>
      <itemselector type="fixedform">
        <identifier uniqueid="AIR FIXEDFORM1" name="AIR FIXEDFORM" label="AIR FIXEDFORM" version="1.0" />
        <itemselectionparameter bpelementid="(SBAC_PT)SBAC Math 6-MATH-6-Spring-2014-2015">
          <property name="slope" value="1" label="slope" />
          <property name="intercept" value="1" label="intercept" />
        </itemselectionparameter>
      </itemselector>
      <segmentform formpartitionid="187-692" />
      <segmentform formpartitionid="187-693" />
      <segmentform formpartitionid="187-694" />
    </adminsegment>
  </administration>
</testspecification>');

CALL loader_main('<?xml version="1.0" encoding="utf-8"?>
<testspecification purpose="administration" publisher="SBAC_PT" publishdate="Aug 28 2015 12:52AM" version="1.0">
  <identifier uniqueid="(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015" name="SBAC Math 7-MATH-7" label="Math GR6-8 Training Test" version="8226" />
  <property name="subject" value="MATH" label="MATH" />
  <property name="grade" value="7" label="grade 7" />
  <property name="grade" value="8" label="grade 8" />
  <property name="type" value="summative" label="summative" />
  <administration>
    <testblueprint>
      <bpelement elementtype="test" minopitems="8" maxopitems="8" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="11">
        <identifier uniqueid="(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015" name="SBAC Math 7-MATH-7" version="8226" />
      </bpelement>
      <bpelement elementtype="segment" minopitems="8" maxopitems="8" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="11">
        <identifier uniqueid="(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015" name="SBAC Math 7-MATH-7" version="8226" />
      </bpelement>
      <bpelement elementtype="strand" minopitems="8" maxopitems="8" opitemcount="0" ftitemcount="11">
        <identifier uniqueid="SBAC_PT-MA-Undesignated" name="MA-Undesignated" version="8226" />
      </bpelement>
    </testblueprint>
    <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="3" />
    <poolproperty property="--ITEMTYPE--" value="GI" label="GI" itemcount="3" />
    <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="2" />
    <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
    <poolproperty property="Language" value="ENU" label="English" itemcount="8" />
    <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="8" />
    <poolproperty property="Language" value="ESN" label="Spanish" itemcount="8" />
    <itempool>
      <testitem filename="item-187-1383.xml" itemtype="EQ">
        <identifier uniqueid="187-1383" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1483.xml" itemtype="EQ">
        <identifier uniqueid="187-1483" version="8233" />
        <bpref>(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1485.xml" itemtype="MC">
        <identifier uniqueid="187-1485" version="8233" />
        <bpref>(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MC" label="MC" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1673.xml" itemtype="TI">
        <identifier uniqueid="187-1673" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="TI" label="TI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="2" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1675.xml" itemtype="MS">
        <identifier uniqueid="187-1675" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MS" label="MS" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1685.xml" itemtype="MI">
        <identifier uniqueid="187-1685" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MI" label="MI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-631.xml" itemtype="EQ">
        <identifier uniqueid="187-631" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-650.xml" itemtype="GI">
        <identifier uniqueid="187-650" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="GI" label="GI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="3" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-652.xml" itemtype="GI">
        <identifier uniqueid="187-652" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="GI" label="GI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-653.xml" itemtype="GI">
        <identifier uniqueid="187-653" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="GI" label="GI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-654.xml" itemtype="MC">
        <identifier uniqueid="187-654" version="8226" />
        <bpref>(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MC" label="MC" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
    </itempool>
    <testform length="8">
      <identifier uniqueid="(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015:Default-ENU" name="(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015:Default-ENU" version="8226" />
      <property name="language" value="ENU" label="English" />
      <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="GI" label="GI" itemcount="3" />
      <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="8" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="5" />
      <poolproperty property="Language" value="ESN" label="Spanish" itemcount="8" />
      <formpartition>
        <identifier uniqueid="187-630" name="Math Training 6-8::SP 15" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-630:I-187-653" name="187-630:I-187-653" version="8226" />
          <groupitem itemid="187-653" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-630:I-187-631" name="187-630:I-187-631" version="8226" />
          <groupitem itemid="187-631" formposition="2" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-630:I-187-1673" name="187-630:I-187-1673" version="8226" />
          <groupitem itemid="187-1673" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-630:I-187-1675" name="187-630:I-187-1675" version="8226" />
          <groupitem itemid="187-1675" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-630:I-187-650" name="187-630:I-187-650" version="8226" />
          <groupitem itemid="187-650" formposition="5" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="6" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-630:I-187-1685" name="187-630:I-187-1685" version="8226" />
          <groupitem itemid="187-1685" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="7" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-630:I-187-654" name="187-630:I-187-654" version="8226" />
          <groupitem itemid="187-654" formposition="7" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="8" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-630:I-187-652" name="187-630:I-187-652" version="8226" />
          <groupitem itemid="187-652" formposition="8" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <testform length="8">
      <identifier uniqueid="(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015:Default-ENU-Braille" name="(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015:Default-ENU-Braille" version="8226" />
      <property name="language" value="ENU-Braille" label="English" />
      <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="3" />
      <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="2" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="5" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="8" />
      <poolproperty property="Language" value="ESN" label="Spanish" itemcount="5" />
      <formpartition>
        <identifier uniqueid="187-632" name="Math Training 6-8::SP 15::Braille" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-632:I-187-1483" name="187-632:I-187-1483" version="8226" />
          <groupitem itemid="187-1483" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-632:I-187-631" name="187-632:I-187-631" version="8226" />
          <groupitem itemid="187-631" formposition="2" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-632:I-187-1673" name="187-632:I-187-1673" version="8226" />
          <groupitem itemid="187-1673" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-632:I-187-1675" name="187-632:I-187-1675" version="8226" />
          <groupitem itemid="187-1675" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-632:I-187-1485" name="187-632:I-187-1485" version="8226" />
          <groupitem itemid="187-1485" formposition="5" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="6" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-632:I-187-1685" name="187-632:I-187-1685" version="8226" />
          <groupitem itemid="187-1685" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="7" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-632:I-187-654" name="187-632:I-187-654" version="8226" />
          <groupitem itemid="187-654" formposition="7" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="8" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-632:I-187-1383" name="187-632:I-187-1383" version="8226" />
          <groupitem itemid="187-1383" formposition="8" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <testform length="8">
      <identifier uniqueid="(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015:Default-ESN" name="(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015:Default-ESN" version="8226" />
      <property name="language" value="ESN" label="Spanish" />
      <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="GI" label="GI" itemcount="3" />
      <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="8" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="5" />
      <poolproperty property="Language" value="ESN" label="Spanish" itemcount="8" />
      <formpartition>
        <identifier uniqueid="187-633" name="Math Training 6-8:: SP 15::Spanish" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-633:I-187-653" name="187-633:I-187-653" version="8226" />
          <groupitem itemid="187-653" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-633:I-187-631" name="187-633:I-187-631" version="8226" />
          <groupitem itemid="187-631" formposition="2" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-633:I-187-1673" name="187-633:I-187-1673" version="8226" />
          <groupitem itemid="187-1673" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-633:I-187-1675" name="187-633:I-187-1675" version="8226" />
          <groupitem itemid="187-1675" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-633:I-187-650" name="187-633:I-187-650" version="8226" />
          <groupitem itemid="187-650" formposition="5" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="6" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-633:I-187-1685" name="187-633:I-187-1685" version="8226" />
          <groupitem itemid="187-1685" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="7" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-633:I-187-654" name="187-633:I-187-654" version="8226" />
          <groupitem itemid="187-654" formposition="7" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="8" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-633:I-187-652" name="187-633:I-187-652" version="8226" />
          <groupitem itemid="187-652" formposition="8" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <adminsegment segmentid="(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015" position="1" itemselection="fixedform">
      <segmentblueprint>
        <segmentbpelement bpelementid="SBAC_PT-MA-Undesignated" minopitems="8" maxopitems="8" />
      </segmentblueprint>
      <itemselector type="fixedform">
        <identifier uniqueid="AIR FIXEDFORM1" name="AIR FIXEDFORM" label="AIR FIXEDFORM" version="1.0" />
        <itemselectionparameter bpelementid="(SBAC_PT)SBAC Math 7-MATH-7-Spring-2014-2015">
          <property name="slope" value="1" label="slope" />
          <property name="intercept" value="1" label="intercept" />
        </itemselectionparameter>
      </itemselector>
      <segmentform formpartitionid="187-630" />
      <segmentform formpartitionid="187-632" />
      <segmentform formpartitionid="187-633" />
    </adminsegment>
  </administration>
</testspecification>');

CALL loader_main('<?xml version="1.0" encoding="utf-8"?>
<testspecification purpose="administration" publisher="SBAC_PT" publishdate="Aug 28 2015 12:52AM" version="1.0">
  <identifier uniqueid="(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015" name="SBAC Math HS-MATH-10" label="Math HS Training Test " version="8226" />
  <property name="subject" value="MATH" label="MATH" />
  <property name="grade" value="10" label="grade 10" />
  <property name="grade" value="11" label="grade 11" />
  <property name="grade" value="12" label="grade 12" />
  <property name="grade" value="9" label="grade 9" />
  <property name="type" value="summative" label="summative" />
  <administration>
    <testblueprint>
      <bpelement elementtype="test" minopitems="7" maxopitems="7" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="9">
        <identifier uniqueid="(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015" name="SBAC Math HS-MATH-10" version="8226" />
      </bpelement>
      <bpelement elementtype="segment" minopitems="7" maxopitems="7" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="9">
        <identifier uniqueid="(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015" name="SBAC Math HS-MATH-10" version="8226" />
      </bpelement>
      <bpelement elementtype="strand" minopitems="7" maxopitems="7" opitemcount="0" ftitemcount="9">
        <identifier uniqueid="SBAC_PT-MA-Undesignated" name="MA-Undesignated" version="8226" />
      </bpelement>
    </testblueprint>
    <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="GI" label="GI" itemcount="2" />
    <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="2" />
    <poolproperty property="--ITEMTYPE--" value="SA" label="SA" itemcount="1" />
    <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
    <poolproperty property="Language" value="ENU" label="English" itemcount="7" />
    <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="7" />
    <poolproperty property="Language" value="ESN" label="Spanish" itemcount="7" />
    <itempool>
      <testitem filename="item-187-1059.xml" itemtype="MS">
        <identifier uniqueid="187-1059" version="8226" />
        <bpref>(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MS" label="MS" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1448.xml" itemtype="MC">
        <identifier uniqueid="187-1448" version="8233" />
        <bpref>(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MC" label="MC" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1673.xml" itemtype="TI">
        <identifier uniqueid="187-1673" version="8226" />
        <bpref>(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="TI" label="TI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="2" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1889.xml" itemtype="GI">
        <identifier uniqueid="187-1889" version="8233" />
        <bpref>(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="GI" label="GI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="IRTGPC" scorepoints="2" weight="1.000000000000000e+000">
          <itemscoreparameter measurementparameter="a" value="5.315500000000000e-001" />
          <itemscoreparameter measurementparameter="b0" value="2.615210000000000e+000" />
          <itemscoreparameter measurementparameter="b1" value="4.885510000000000e+000" />
        </itemscoredimension>
      </testitem>
      <testitem filename="item-187-2065.xml" itemtype="SA">
        <identifier uniqueid="187-2065" version="8233" />
        <bpref>(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="SA" label="SA" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-2068.xml" itemtype="MS">
        <identifier uniqueid="187-2068" version="8233" />
        <bpref>(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MS" label="MS" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-631.xml" itemtype="EQ">
        <identifier uniqueid="187-631" version="8226" />
        <bpref>(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-666.xml" itemtype="MI">
        <identifier uniqueid="187-666" version="8226" />
        <bpref>(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="MI" label="MI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ENU-Braille" label="Braille English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-766.xml" itemtype="GI">
        <identifier uniqueid="187-766" version="8226" />
        <bpref>(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-MA-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="GI" label="GI" />
        <poolproperty property="Language" value="ENU" label="English" />
        <poolproperty property="Language" value="ESN" label="Spanish" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="1" weight="1.000000000000000e+000" />
      </testitem>
    </itempool>
    <testform length="7">
      <identifier uniqueid="(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015:Default-ENU" name="(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015:Default-ENU" version="8226" />
      <property name="language" value="ENU" label="English" />
      <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="GI" label="GI" itemcount="2" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="SA" label="SA" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="7" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="5" />
      <poolproperty property="Language" value="ESN" label="Spanish" itemcount="7" />
      <formpartition>
        <identifier uniqueid="187-631" name="Math Training HS::SP 15" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-631:I-187-1889" name="187-631:I-187-1889" version="8226" />
          <groupitem itemid="187-1889" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-631:I-187-2065" name="187-631:I-187-2065" version="8226" />
          <groupitem itemid="187-2065" formposition="2" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-631:I-187-666" name="187-631:I-187-666" version="8226" />
          <groupitem itemid="187-666" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-631:I-187-631" name="187-631:I-187-631" version="8226" />
          <groupitem itemid="187-631" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-631:I-187-1059" name="187-631:I-187-1059" version="8226" />
          <groupitem itemid="187-1059" formposition="5" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="6" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-631:I-187-1673" name="187-631:I-187-1673" version="8226" />
          <groupitem itemid="187-1673" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="7" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-631:I-187-766" name="187-631:I-187-766" version="8226" />
          <groupitem itemid="187-766" formposition="7" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <testform length="7">
      <identifier uniqueid="(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015:Default-ENU-Braille" name="(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015:Default-ENU-Braille" version="8226" />
      <property name="language" value="ENU-Braille" label="English" />
      <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="2" />
      <poolproperty property="--ITEMTYPE--" value="SA" label="SA" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="5" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="7" />
      <poolproperty property="Language" value="ESN" label="Spanish" itemcount="5" />
      <formpartition>
        <identifier uniqueid="187-634" name="Math Training HS::SP 15::Braille" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-634:I-187-2068" name="187-634:I-187-2068" version="8226" />
          <groupitem itemid="187-2068" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-634:I-187-2065" name="187-634:I-187-2065" version="8226" />
          <groupitem itemid="187-2065" formposition="2" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-634:I-187-666" name="187-634:I-187-666" version="8226" />
          <groupitem itemid="187-666" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-634:I-187-631" name="187-634:I-187-631" version="8226" />
          <groupitem itemid="187-631" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-634:I-187-1059" name="187-634:I-187-1059" version="8226" />
          <groupitem itemid="187-1059" formposition="5" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="6" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-634:I-187-1673" name="187-634:I-187-1673" version="8226" />
          <groupitem itemid="187-1673" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="7" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-634:I-187-1448" name="187-634:I-187-1448" version="8226" />
          <groupitem itemid="187-1448" formposition="7" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <testform length="7">
      <identifier uniqueid="(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015:Default-ESN" name="(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015:Default-ESN" version="8226" />
      <property name="language" value="ESN" label="Spanish" />
      <poolproperty property="--ITEMTYPE--" value="EQ" label="EQ" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="GI" label="GI" itemcount="2" />
      <poolproperty property="--ITEMTYPE--" value="MI" label="MI" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="MS" label="MS" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="SA" label="SA" itemcount="1" />
      <poolproperty property="--ITEMTYPE--" value="TI" label="TI" itemcount="1" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="7" />
      <poolproperty property="Language" value="ENU-Braille" label="Braille English" itemcount="5" />
      <poolproperty property="Language" value="ESN" label="Spanish" itemcount="7" />
      <formpartition>
        <identifier uniqueid="187-635" name="Math Training HS::SP 15::Spanish" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-635:I-187-1889" name="187-635:I-187-1889" version="8226" />
          <groupitem itemid="187-1889" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-635:I-187-2065" name="187-635:I-187-2065" version="8226" />
          <groupitem itemid="187-2065" formposition="2" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-635:I-187-666" name="187-635:I-187-666" version="8226" />
          <groupitem itemid="187-666" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-635:I-187-631" name="187-635:I-187-631" version="8226" />
          <groupitem itemid="187-631" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-635:I-187-1059" name="187-635:I-187-1059" version="8226" />
          <groupitem itemid="187-1059" formposition="5" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="6" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-635:I-187-1673" name="187-635:I-187-1673" version="8226" />
          <groupitem itemid="187-1673" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="7" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-635:I-187-766" name="187-635:I-187-766" version="8226" />
          <groupitem itemid="187-766" formposition="7" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <adminsegment segmentid="(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015" position="1" itemselection="fixedform">
      <segmentblueprint>
        <segmentbpelement bpelementid="SBAC_PT-MA-Undesignated" minopitems="7" maxopitems="7" />
      </segmentblueprint>
      <itemselector type="fixedform">
        <identifier uniqueid="AIR FIXEDFORM1" name="AIR FIXEDFORM" label="AIR FIXEDFORM" version="1.0" />
        <itemselectionparameter bpelementid="(SBAC_PT)SBAC Math HS-MATH-10-Spring-2014-2015">
          <property name="slope" value="1" label="slope" />
          <property name="intercept" value="1" label="intercept" />
        </itemselectionparameter>
      </itemselector>
      <segmentform formpartitionid="187-631" />
      <segmentform formpartitionid="187-634" />
      <segmentform formpartitionid="187-635" />
    </adminsegment>
  </administration>
</testspecification>');

CALL loader_main('<?xml version="1.0" encoding="utf-8"?>
<testspecification purpose="administration" publisher="SBAC_PT" publishdate="Aug 28 2015 12:55AM" version="1.0">
  <identifier uniqueid="(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015" name="SBACTraining-Student Help-11" label="Grades 3 - 11 Student Help" version="8226" />
  <property name="subject" value="Student Help" label="Student Help" />
  <property name="grade" value="10" label="grade 10" />
  <property name="grade" value="11" label="grade 11" />
  <property name="grade" value="3" label="grade 3" />
  <property name="grade" value="4" label="grade 4" />
  <property name="grade" value="5" label="grade 5" />
  <property name="grade" value="6" label="grade 6" />
  <property name="grade" value="7" label="grade 7" />
  <property name="grade" value="8" label="grade 8" />
  <property name="grade" value="9" label="grade 9" />
  <property name="type" value="summative" label="summative" />
  <administration>
    <testblueprint>
      <bpelement elementtype="test" minopitems="53" maxopitems="53" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="53">
        <identifier uniqueid="(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015" name="SBACTraining-Student Help-11" version="8226" />
      </bpelement>
      <bpelement elementtype="segment" minopitems="53" maxopitems="53" minftitems="0" maxftitems="0" opitemcount="0" ftitemcount="53">
        <identifier uniqueid="(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015" name="SBACTraining-Student Help-11" version="8226" />
      </bpelement>
      <bpelement elementtype="strand" minopitems="53" maxopitems="53" opitemcount="0" ftitemcount="53">
        <identifier uniqueid="SBAC_PT-SH-Undesignated" name="SH-Undesignated" version="8226" />
      </bpelement>
    </testblueprint>
    <poolproperty property="--ITEMTYPE--" value="TUT" label="TUT" itemcount="9" />
    <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" itemcount="44" />
    <poolproperty property="Language" value="ENU" label="English" itemcount="53" />
    <itempool>
      <testitem filename="item-187-1072.xml" itemtype="TUT">
        <identifier uniqueid="187-1072" version="8233" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="TUT" label="TUT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1073.xml" itemtype="TUT">
        <identifier uniqueid="187-1073" version="8233" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="TUT" label="TUT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1074.xml" itemtype="TUT">
        <identifier uniqueid="187-1074" version="8233" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="TUT" label="TUT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1075.xml" itemtype="TUT">
        <identifier uniqueid="187-1075" version="8233" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="TUT" label="TUT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1076.xml" itemtype="TUT">
        <identifier uniqueid="187-1076" version="8233" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="TUT" label="TUT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1078.xml" itemtype="TUT">
        <identifier uniqueid="187-1078" version="8233" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="TUT" label="TUT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1080.xml" itemtype="TUT">
        <identifier uniqueid="187-1080" version="8233" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="TUT" label="TUT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1090.xml" itemtype="WIT">
        <identifier uniqueid="187-1090" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1300.xml" itemtype="WIT">
        <identifier uniqueid="187-1300" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1303.xml" itemtype="WIT">
        <identifier uniqueid="187-1303" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1311.xml" itemtype="WIT">
        <identifier uniqueid="187-1311" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1312.xml" itemtype="WIT">
        <identifier uniqueid="187-1312" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1342.xml" itemtype="WIT">
        <identifier uniqueid="187-1342" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1503.xml" itemtype="WIT">
        <identifier uniqueid="187-1503" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1710.xml" itemtype="WIT">
        <identifier uniqueid="187-1710" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1717.xml" itemtype="WIT">
        <identifier uniqueid="187-1717" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1721.xml" itemtype="WIT">
        <identifier uniqueid="187-1721" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1722.xml" itemtype="WIT">
        <identifier uniqueid="187-1722" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1723.xml" itemtype="WIT">
        <identifier uniqueid="187-1723" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1725.xml" itemtype="WIT">
        <identifier uniqueid="187-1725" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1726.xml" itemtype="WIT">
        <identifier uniqueid="187-1726" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1732.xml" itemtype="WIT">
        <identifier uniqueid="187-1732" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1733.xml" itemtype="WIT">
        <identifier uniqueid="187-1733" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1734.xml" itemtype="WIT">
        <identifier uniqueid="187-1734" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1735.xml" itemtype="WIT">
        <identifier uniqueid="187-1735" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1737.xml" itemtype="WIT">
        <identifier uniqueid="187-1737" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1742.xml" itemtype="WIT">
        <identifier uniqueid="187-1742" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1747.xml" itemtype="WIT">
        <identifier uniqueid="187-1747" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1749.xml" itemtype="WIT">
        <identifier uniqueid="187-1749" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1750.xml" itemtype="WIT">
        <identifier uniqueid="187-1750" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1752.xml" itemtype="WIT">
        <identifier uniqueid="187-1752" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1753.xml" itemtype="WIT">
        <identifier uniqueid="187-1753" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1754.xml" itemtype="WIT">
        <identifier uniqueid="187-1754" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1755.xml" itemtype="WIT">
        <identifier uniqueid="187-1755" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1756.xml" itemtype="WIT">
        <identifier uniqueid="187-1756" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1757.xml" itemtype="WIT">
        <identifier uniqueid="187-1757" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1758.xml" itemtype="WIT">
        <identifier uniqueid="187-1758" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1761.xml" itemtype="WIT">
        <identifier uniqueid="187-1761" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1766.xml" itemtype="WIT">
        <identifier uniqueid="187-1766" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1767.xml" itemtype="WIT">
        <identifier uniqueid="187-1767" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1768.xml" itemtype="TUT">
        <identifier uniqueid="187-1768" version="8233" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="TUT" label="TUT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-1769.xml" itemtype="TUT">
        <identifier uniqueid="187-1769" version="8233" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="TUT" label="TUT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-2284.xml" itemtype="WIT">
        <identifier uniqueid="187-2284" version="8233" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-2287.xml" itemtype="WIT">
        <identifier uniqueid="187-2287" version="8233" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-2312.xml" itemtype="WIT">
        <identifier uniqueid="187-2312" version="8233" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-2320.xml" itemtype="WIT">
        <identifier uniqueid="187-2320" version="8233" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-2350.xml" itemtype="WIT">
        <identifier uniqueid="187-2350" version="8233" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-2377.xml" itemtype="WIT">
        <identifier uniqueid="187-2377" version="8233" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-787.xml" itemtype="WIT">
        <identifier uniqueid="187-787" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-788.xml" itemtype="WIT">
        <identifier uniqueid="187-788" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-797.xml" itemtype="WIT">
        <identifier uniqueid="187-797" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-800.xml" itemtype="WIT">
        <identifier uniqueid="187-800" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
      <testitem filename="item-187-801.xml" itemtype="WIT">
        <identifier uniqueid="187-801" version="8226" />
        <bpref>(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015</bpref>
        <bpref>SBAC_PT-SH-Undesignated</bpref>
        <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" />
        <poolproperty property="Language" value="ENU" label="English" />
        <itemscoredimension measurementmodel="RAWSCORE" scorepoints="-1" weight="1.000000000000000e+000" />
      </testitem>
    </itempool>
    <testform length="53">
      <identifier uniqueid="(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015:Default-ENU" name="(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015:Default-ENU" version="8226" />
      <property name="language" value="ENU" label="English" />
      <poolproperty property="--ITEMTYPE--" value="TUT" label="TUT" itemcount="9" />
      <poolproperty property="--ITEMTYPE--" value="WIT" label="WIT" itemcount="44" />
      <poolproperty property="Language" value="ENU" label="English" itemcount="53" />
      <formpartition>
        <identifier uniqueid="187-691" name="SS TT 2015" version="8226" />
        <itemgroup formposition="1" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1072" name="187-691:I-187-1072" version="8226" />
          <groupitem itemid="187-1072" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="2" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1073" name="187-691:I-187-1073" version="8226" />
          <groupitem itemid="187-1073" formposition="2" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="3" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1074" name="187-691:I-187-1074" version="8226" />
          <groupitem itemid="187-1074" formposition="3" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="4" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1075" name="187-691:I-187-1075" version="8226" />
          <groupitem itemid="187-1075" formposition="4" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="5" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1078" name="187-691:I-187-1078" version="8226" />
          <groupitem itemid="187-1078" formposition="5" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="6" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1080" name="187-691:I-187-1080" version="8226" />
          <groupitem itemid="187-1080" formposition="6" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="7" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1768" name="187-691:I-187-1768" version="8226" />
          <groupitem itemid="187-1768" formposition="7" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="8" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1769" name="187-691:I-187-1769" version="8226" />
          <groupitem itemid="187-1769" formposition="8" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="9" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1753" name="187-691:I-187-1753" version="8226" />
          <groupitem itemid="187-1753" formposition="9" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="10" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1732" name="187-691:I-187-1732" version="8226" />
          <groupitem itemid="187-1732" formposition="10" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="11" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1733" name="187-691:I-187-1733" version="8226" />
          <groupitem itemid="187-1733" formposition="11" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="12" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1734" name="187-691:I-187-1734" version="8226" />
          <groupitem itemid="187-1734" formposition="12" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="13" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1735" name="187-691:I-187-1735" version="8226" />
          <groupitem itemid="187-1735" formposition="13" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="14" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1737" name="187-691:I-187-1737" version="8226" />
          <groupitem itemid="187-1737" formposition="14" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="15" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1766" name="187-691:I-187-1766" version="8226" />
          <groupitem itemid="187-1766" formposition="15" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="16" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1767" name="187-691:I-187-1767" version="8226" />
          <groupitem itemid="187-1767" formposition="16" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="17" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1747" name="187-691:I-187-1747" version="8226" />
          <groupitem itemid="187-1747" formposition="17" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="18" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1761" name="187-691:I-187-1761" version="8226" />
          <groupitem itemid="187-1761" formposition="18" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="19" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1749" name="187-691:I-187-1749" version="8226" />
          <groupitem itemid="187-1749" formposition="19" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="20" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1752" name="187-691:I-187-1752" version="8226" />
          <groupitem itemid="187-1752" formposition="20" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="21" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1754" name="187-691:I-187-1754" version="8226" />
          <groupitem itemid="187-1754" formposition="21" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="22" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1750" name="187-691:I-187-1750" version="8226" />
          <groupitem itemid="187-1750" formposition="22" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="23" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-797" name="187-691:I-187-797" version="8226" />
          <groupitem itemid="187-797" formposition="23" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="24" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-788" name="187-691:I-187-788" version="8226" />
          <groupitem itemid="187-788" formposition="24" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="25" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-801" name="187-691:I-187-801" version="8226" />
          <groupitem itemid="187-801" formposition="25" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="26" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1710" name="187-691:I-187-1710" version="8226" />
          <groupitem itemid="187-1710" formposition="26" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="27" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-787" name="187-691:I-187-787" version="8226" />
          <groupitem itemid="187-787" formposition="27" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="28" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-800" name="187-691:I-187-800" version="8226" />
          <groupitem itemid="187-800" formposition="28" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="29" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1090" name="187-691:I-187-1090" version="8226" />
          <groupitem itemid="187-1090" formposition="29" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="30" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1722" name="187-691:I-187-1722" version="8226" />
          <groupitem itemid="187-1722" formposition="30" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="31" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1725" name="187-691:I-187-1725" version="8226" />
          <groupitem itemid="187-1725" formposition="31" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="32" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1300" name="187-691:I-187-1300" version="8226" />
          <groupitem itemid="187-1300" formposition="32" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="33" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1312" name="187-691:I-187-1312" version="8226" />
          <groupitem itemid="187-1312" formposition="33" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="34" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1311" name="187-691:I-187-1311" version="8226" />
          <groupitem itemid="187-1311" formposition="34" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="35" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1303" name="187-691:I-187-1303" version="8226" />
          <groupitem itemid="187-1303" formposition="35" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="36" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1717" name="187-691:I-187-1717" version="8226" />
          <groupitem itemid="187-1717" formposition="36" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="37" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-2284" name="187-691:I-187-2284" version="8226" />
          <groupitem itemid="187-2284" formposition="37" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="38" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-2287" name="187-691:I-187-2287" version="8226" />
          <groupitem itemid="187-2287" formposition="38" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="39" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1721" name="187-691:I-187-1721" version="8226" />
          <groupitem itemid="187-1721" formposition="39" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="40" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1723" name="187-691:I-187-1723" version="8226" />
          <groupitem itemid="187-1723" formposition="40" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="41" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1726" name="187-691:I-187-1726" version="8226" />
          <groupitem itemid="187-1726" formposition="41" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="42" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1742" name="187-691:I-187-1742" version="8226" />
          <groupitem itemid="187-1742" formposition="42" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="43" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1342" name="187-691:I-187-1342" version="8226" />
          <groupitem itemid="187-1342" formposition="43" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="44" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1503" name="187-691:I-187-1503" version="8226" />
          <groupitem itemid="187-1503" formposition="44" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="45" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-2350" name="187-691:I-187-2350" version="8226" />
          <groupitem itemid="187-2350" formposition="45" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="46" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-2312" name="187-691:I-187-2312" version="8226" />
          <groupitem itemid="187-2312" formposition="46" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="47" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-2377" name="187-691:I-187-2377" version="8226" />
          <groupitem itemid="187-2377" formposition="47" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="48" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-2320" name="187-691:I-187-2320" version="8226" />
          <groupitem itemid="187-2320" formposition="48" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="49" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1076" name="187-691:I-187-1076" version="8226" />
          <groupitem itemid="187-1076" formposition="49" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="50" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1755" name="187-691:I-187-1755" version="8226" />
          <groupitem itemid="187-1755" formposition="50" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="51" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1756" name="187-691:I-187-1756" version="8226" />
          <groupitem itemid="187-1756" formposition="51" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="52" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1757" name="187-691:I-187-1757" version="8226" />
          <groupitem itemid="187-1757" formposition="52" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
        <itemgroup formposition="53" maxitems="ALL" maxresponses="0">
          <identifier uniqueid="187-691:I-187-1758" name="187-691:I-187-1758" version="8226" />
          <groupitem itemid="187-1758" formposition="53" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="true" blockid="A" />
        </itemgroup>
      </formpartition>
    </testform>
    <adminsegment segmentid="(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015" position="1" itemselection="fixedform">
      <segmentblueprint>
        <segmentbpelement bpelementid="SBAC_PT-SH-Undesignated" minopitems="53" maxopitems="53" />
      </segmentblueprint>
      <itemselector type="fixedform">
        <identifier uniqueid="AIR FIXEDFORM1" name="AIR FIXEDFORM" label="AIR FIXEDFORM" version="1.0" />
        <itemselectionparameter bpelementid="(SBAC_PT)SBAC-Student Help-11-Spring-2014-2015">
          <property name="slope" value="1" label="slope" />
          <property name="intercept" value="1" label="intercept" />
        </itemselectionparameter>
      </itemselector>
      <segmentform formpartitionid="187-691" />
    </adminsegment>
  </administration>
</testspecification>');
SET SQL_SAFE_UPDATES = 1;
