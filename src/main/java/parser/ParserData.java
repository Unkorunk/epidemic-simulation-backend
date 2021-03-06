package parser;

import java.util.ArrayList;
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
            var first = data.get(cur);
            var second = pd.data.get(pdCur);

            int cmp = first.date.compareTo(second.date);

            if (cmp < 0) {
                mergedData.add(data.get(cur));
                cur++;
            } else if (cmp > 0) {
                mergedData.add(pd.data.get(pdCur));
                pdCur++;
            } else {
                data.get(cur).Merge(pd.data.get(pdCur));
                mergedData.add(data.get(cur));
                pdCur++;
                cur++;
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

    public void Sort() {
        Collections.sort(data);
    }
}
