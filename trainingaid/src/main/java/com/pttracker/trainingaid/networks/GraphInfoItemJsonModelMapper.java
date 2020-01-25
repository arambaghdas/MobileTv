package com.pttracker.trainingaid.networks;

import com.pttracker.trainingaid.models.GraphInfoItem;
import com.pttrackershared.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Converts GraphInfoItemJsonModel to GraphInfoItem
 */
public class GraphInfoItemJsonModelMapper {
    public GraphInfoItem convertToGraphInfoItem(GraphInfoItemJsonModel graphInfoItemJsonModel) {
        GraphInfoItem graphInfoItem = new GraphInfoItem();
        if(graphInfoItemJsonModel.getWeight()!=null)
            graphInfoItem.setWeight(Float.parseFloat(graphInfoItemJsonModel.getWeight()));

        if(graphInfoItemJsonModel.getBmi()!= null)
            graphInfoItem.setBmi(graphInfoItemJsonModel.getBmi());

        if(graphInfoItemJsonModel.getDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                graphInfoItem.setDate(dateFormat.parse(graphInfoItemJsonModel.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        graphInfoItem.setWorkoutName(graphInfoItemJsonModel.getWorkoutname());
        graphInfoItem.setTimeComplete(graphInfoItemJsonModel.getTimeComplete());
        return graphInfoItem;
    }
}

