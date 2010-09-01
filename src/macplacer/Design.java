/*
 *************************************************************************
 *************************************************************************
 **                                                                     **
 **  MACPLACER                                                          **
 **  Copyright (C) 2010         Karl W. Pfalzer                         **
 **                                                                     **
 **  This program is free software; you can redistribute it and/or      **
 **  modify it under the terms of the GNU General Public License        **
 **  as published by the Free Software Foundation; either version 2     **
 **  of the License, or (at your option) any later version.             **
 **                                                                     **
 **  This program is distributed in the hope that it will be useful,    **
 **  but WITHOUT ANY WARRANTY; without even the implied warranty of     **
 **  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the      **
 **  GNU General Public License for more details.                       **
 **                                                                     **
 **  You should have received a copy of the GNU General Public License  **
 **  along with this program; if not, write to the                      **
 **  Free Software Foundation, Inc.                                     **
 **  51 Franklin Street, Fifth Floor                                    **
 **  Boston, MA  02110-1301, USA.                                       **
 **                                                                     **
 *************************************************************************
 *************************************************************************
 */
package macplacer;
import static macplacer.Util.error;
import  java.io.IOException;
import	java.io.FileInputStream;
import	java.util.HashMap;
import	java.util.ArrayList;
import	java.util.List;
import	org.xml.sax.Attributes;
import	org.xml.sax.SAXException;
import  org.xml.sax.XMLReader;
import	org.xml.sax.ContentHandler;
import  org.xml.sax.InputSource;
import  org.xml.sax.Locator;
import  org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author karl
 */
public class Design {
	public Design(String xmlFname) throws SAXException, IOException {
		FileInputStream fis = new FileInputStream(xmlFname);
		XMLReader rdr = XMLReaderFactory.createXMLReader();
		rdr.setContentHandler(new MyContentHandler());
		rdr.parse(new InputSource(fis));
		fis.close();
	}

	public static void main(String argv[]) {
		try {
			Design des = new Design(argv[0]);
			new DefaultAlgorithm().getClusters(des);
		} catch (Exception ex) {
			error(ex);
		}
	}

	public List<Instance> getInstances() {
		return m_instances;
	}

	private void initialize() {
		((ArrayList)m_instances).trimToSize();
	}

	private HashMap<String,LibCell>	m_libCellsByName = new HashMap<String, LibCell>();
	private List<Instance>			m_instances = new ArrayList<Instance>(100);
	private String					m_designName;
	public final static String		stHierSep = "/";

	private class MyContentHandler implements ContentHandler {
        private static final int LIB = 1;
		private static final int COMP = 2;
		
		private int m_state = 0;

		public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
            if (qName.equals("design")) {
				m_designName = atts.getValue("name");
			} else if (qName.equals("lib")) {
				m_state = LIB;
			} else if ((LIB == m_state) && qName.equals("cell")) {
				LibCell lc = new LibCell(atts);
				m_libCellsByName.put(lc.getName(), lc);
			} else if (qName.equals("components")) {
				m_state = COMP;
			} else if ((COMP == m_state) && qName.equals("comp")) {
				LibCell lc = m_libCellsByName.get(atts.getValue("ref"));
				m_instances.add(new Instance(atts.getValue("name"), lc));
			}
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
			if (qName.equals("lib")) {
				m_state = 0;
			} else if (qName.equals("components")) {
				m_state = 0;
			}
		}

        public void endPrefixMapping(String prefix) throws SAXException {}

        public void endDocument() throws SAXException {}

        public void setDocumentLocator(Locator locator) {}

        public void skippedEntity(String name) throws SAXException {}

        public void startDocument() throws SAXException {}

        public void startPrefixMapping(String prefix, String uri) throws SAXException {}

        public void processingInstruction(String target, String data) throws SAXException {}

        public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}

        public void characters(char[] ch, int start, int length) throws SAXException {}

	}

}
