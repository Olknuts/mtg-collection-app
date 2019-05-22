package se.dala.mtg_collection_app.init;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import se.dala.mtg_collection_app.model.ExpansionSymbol;

public class ExpansionSymbolInitializer {
    private String symbols;
    static List<String> expansionName = new ArrayList<>();

    public static List<ExpansionSymbol> symbolInitializer(Context context) {
        List<ExpansionSymbol> expansionSymbols = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(context.getAssets().open("sets.txt"), "UTF-8"))) {
            String mLine = reader.readLine();
            setExpansionNames();
            for (int i = 0; i < mLine.length()-1; i++) {
                expansionSymbols.add(new ExpansionSymbol(mLine.charAt(i) + "", expansionName.get(i)));
                //System.out.println(expansionName.get(i) + " + "+ mLine.charAt(i));
            }
        } catch (IOException e) {
            //log the exception
        }
        //log the exception
        return expansionSymbols;
    }

    private static void setExpansionNames() {
        expansionName.add("m10");
        expansionName.add("m11");
        expansionName.add("m12");
        expansionName.add("m13");
        expansionName.add("m14");
        expansionName.add("m15");
        expansionName.add("som");
        expansionName.add("mbs");
        expansionName.add("nph");
        expansionName.add("isd");
        expansionName.add("dka");
        expansionName.add("avr");
        expansionName.add("rtr");
        expansionName.add("gtc");
        expansionName.add("dgm");
        expansionName.add("ths");
        expansionName.add("bng");
        expansionName.add("ktk");
        expansionName.add("frf");
        expansionName.add("cns");
        expansionName.add("dtk");
        expansionName.add("ori");
        expansionName.add("bfz");
        expansionName.add("ogw");
        expansionName.add("soi");
        expansionName.add("ema");
        expansionName.add("emn");
        expansionName.add("kld");
        expansionName.add("aer");
        expansionName.add("akh");
        expansionName.add("hou");
        expansionName.add("xln");
        expansionName.add("rix");
        expansionName.add("dom");
        expansionName.add("m19");
    }
}

