package se.dala.mtg_collection_app.model;

public class ExpansionSymbol {

    private final String symbol;
    private final String name;

    public ExpansionSymbol(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }
}
