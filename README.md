# ctap-features

This project is a module of the Common Text Analysis Platform, or CTAP system,
which can be accessed from http://ctapweb.com. 

The CTAP project contains two components: a Web frontend and the feature
extractors backend. The present project is the latter component.

The project contains analysis engines written in the classic
[UIMA](https://uima.apache.org/) manner---each AE is made up of two parts: an XML descriptor and an accompanying class file. This engines are usable in themselves. However, they are designed to be imported into the CTAP systems. So certain conventions are followed. The functions and analysis that the annotators and analysis engines realize are quite self-explanatory by looking at their names and the description in the XML file.

The CTAP system uses these analysis engines to create feature extraction
pipelines, which mimics the process of creating an Aggregate Analysis Engine in
UIMA. The CTAP system then calls these pipelines based on user choices of the
textual features they want for analyzing their corpora. As a result, the
analysis engines included in this package are the backbones of the CTAP system.
They determine the analytical functionalities of the system.  

Users are free to use the analysis engines in this project for their own
ppackagerojects. If anybody is interested in helping us expand the collection of
analysis engines, please contact the CTAP group at http://ctapweb.com. 

This software is licensed under the BSD License.
