package com.mda.roomlibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONObject;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class Converter extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xmlto_json);

        String xmlString="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<library>\n" +
                "    <owner>John Doe</owner>\n" +
                "    <book id=\"007\">James Bond</book>\n" +
                "    <book id=\"000\">Book for the dummies</book>\n" +
                "</library>";


        TextView txt_show = (TextView)findViewById(R.id.txt_show);

        XmlToJson xmlToJson = new XmlToJson.Builder(xmlString).build();


        txt_show.setText(xmlToJson.toString());
    }

}
