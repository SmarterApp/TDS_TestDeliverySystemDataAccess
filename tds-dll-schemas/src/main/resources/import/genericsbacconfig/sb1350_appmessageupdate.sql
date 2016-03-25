update configs.client_messagetranslation  T, configs.tds_coremessageobject O
set T.message = '<p>This page allows you to check the <strong>current</strong> bandwidth of your network. The following operating systems and web browsers are supported: </p>  <ul>  <li>Windows XP, Vista, 7, 8.0, 8.1: Mozilla Firefox</li>  <li>Mac OS 10.4.4&#8211;10.9: Firefox</li>  <li>Linux Fedora Core 6 or 16; Ubuntu 9&#8211;11.10: Firefox</li>    <li>Apple tablets (iPad) running iOS 6.0-7.0: Safari</li>  <li>Android tablets running 4.0.4-4.2: Google Chrome</li> <li>Chromeos 31+: Google Chrome</li> </ul>       <p>To determine your bandwidth, select a test from the drop-down list and enter the maximum number of students likely to test at one time, then click [Run Network Diagnostics Tests].  </p>     <p>The [<strong>Text-to-Speech Check</strong>] is for schools who will be administering the Field test and requires the use of the secure browser. The secure browser is available from <a target="_blank" href="ftp://ftps.smarterbalanced.org/~sbacpublic/Public/SecureBrowsers/">ftp://ftps.smarterbalanced.org/~sbacpublic/Public/SecureBrowsers/</a>.  </p>
'
where T._fk_coremessageobject = O._key and O.appkey= 'Diagnostics.Label.IntroText' 
and T.language = 'ENU' and client <> 'AIR';

update configs.__appmessages set message = '<p>This page allows you to check the <strong>current</strong> bandwidth of your network. The following operating systems and web browsers are supported: </p>  <ul>  <li>Windows XP, Vista, 7, 8.0, 8.1: Mozilla Firefox</li>  <li>Mac OS 10.4.4&#8211;10.9: Firefox</li>  <li>Linux Fedora Core 6 or 16; Ubuntu 9&#8211;11.10: Firefox</li>    <li>Apple tablets (iPad) running iOS 6.0-7.0: Safari</li>  <li>Android tablets running 4.0.4-4.2: Google Chrome</li> <li>Chromeos 31+: Google Chrome</li> </ul>       <p>To determine your bandwidth, select a test from the drop-down list and enter the maximum number of students likely to test at one time, then click [Run Network Diagnostics Tests].  </p>     <p>The [<strong>Text-to-Speech Check</strong>] is for schools who will be administering the Field test and requires the use of the secure browser. The secure browser is available from <a target="_blank" href="ftp://ftps.smarterbalanced.org/~sbacpublic/Public/SecureBrowsers/">ftp://ftps.smarterbalanced.org/~sbacpublic/Public/SecureBrowsers/</a>.  </p>
'
where appkey='Diagnostics.Label.IntroText';
