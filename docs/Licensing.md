> *Refer to* [*README*](../README.md) *for main instruction file*

This source code is **dual licensed (GPL/commercial)**, and contains third-party libraries compatible with commercial and proprietary applications.

Refer to [Commercial License](Commercial_License.md) for commercial licensing.

Regarding third-party libraries it depends on doubango framework and IMSDroid (also from Doubango), whose dependencies are collected in [Doubango Licensing](Licensing_Doubango.md).

Additionally, in order to provide **AMR-WB** encoding capabilities needed by MCPTT, and XML parsing (libxml, now mandatory), the following additional libraries have been used. 

Additionally, some versions of the libraries contained in the original [Doubango Licensing](Licensing_Doubango.md) file have been updated, so please check the changes in the following table:


<table border='1' cellspacing='0' cellpadding='5'>
<blockquote><tr>
<blockquote><td>Name</td>
<td>Version</td>
<td>License</td>
<td>Dependency</td>
<td>Comments</td>
</blockquote></tr>
<tr>
<blockquote><td><a href='https://sourceforge.net/projects/opencore-amr/vo-amrwbenc'>opencore-amr VisualON AMR-WB encoder</a></td>
<td>0.1.2</td>
<td>Apache license 2.0</td>
<td><i>Needed for MCPTT compliance</i></td>
<td>Needed for AMR encoding, included in opencore-amr, also used for decoding</td>
</blockquote></tr>
</blockquote><blockquote>
<blockquote><td><a href='http://www.xmlsoft.org/'>libxml2</a></td>
<td>2.9.1</td>
<td>MIT</td>
<td><i>Needed for MCPTT compliance</i></td>
<td>Needed for XML</td>
</blockquote></tr>
</blockquote><blockquote></table>
</br>

Please, check the AMR-WB licensing and patents constraints in your development platform and Android device according to your national laws before using it even for research purposes. See for example the disclaimer in VisualON AMR-WB encoder library:

*THIS IS NOT A GRANT OF PATENT RIGHTS.*

*Google makes no representation or warranty that the codecs for which source code is made available hereunder are unencumbered by third-party patents.  Those intending to use this source code in hardware or software products are advised that implementations of these codecs, including in open source software or shareware, may require patent licenses from the relevant patent holders.*


