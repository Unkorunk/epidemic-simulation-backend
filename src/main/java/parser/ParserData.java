package parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class ParserData {
    public ParserData() {
        data = new ArrayList<>();
    }

    public String source;

    public ArrayList<DayData> data;

    public void Merge(ParserData pd) {
        Collections.sort(pd.data);
        Collections.sort(data);

        var mergedData = new ArrayList<DayData>();
        int cur = 0, pdCur = 0;

        while (cur < data.size() && pdCur < pd.data.size()) {
            switch (data.get(cur).date.compareTo(pd.data.get(pdCur).date)) {
                case -1:
                    mergedData.add(data.get(cur));
                    cur++;
                    break;
                case 1:
                    mergedData.add(pd.data.get(pdCur));
                    pdCur++;
                    break;
                default:
                    data.get(cur).Merge(pd.data.get(pdCur));
                    mergedData.add(data.get(cur));
                    pdCur++;
                    cur++;
                    break;
            }
        }

        while (cur < data.size()) {
            mergedData.add(data.get(cur));
            cur++;
        }

        while (pdCur < pd.data.size()) {
            mergedData.add(pd.data.get(pdCur));
            pdCur++;
        }

        this.data = mergedData;
    }
}
