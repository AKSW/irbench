package org.aksw.orbit.benchmark.qald.schema;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AdapterCDATA extends XmlAdapter<java.lang.String, java.lang.String> {

    @Override
    public java.lang.String marshal(java.lang.String arg0) throws Exception {
        return "<![CDATA[" + arg0 + "]]>";
    }

    @Override
    public java.lang.String unmarshal(java.lang.String arg0) throws Exception {
        return arg0;
    }

}