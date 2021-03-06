package parser;

import java.util.List;

public abstract class Parser {
    public abstract ParserData Parse();

    public static ParserData Merge(List<ParserData> parserDataList) {
        var res = new ParserData();

        StringBuilder sb = new StringBuilder();

        for (var pd : parserDataList) {
            sb.append(pd.source);
            sb.append("; ");

            res.Merge(pd);
        }

        res.source = sb.toString();

        return res;
    }
}
