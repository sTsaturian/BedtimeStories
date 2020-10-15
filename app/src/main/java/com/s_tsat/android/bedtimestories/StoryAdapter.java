package com.s_tsat.android.bedtimestories;

import androidx.annotation.NonNull;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

/**
 * This class provides views for the story list, given a list of Story objects
 *
 * @author Sergei Tsaturian
 */

public class StoryAdapter extends ArrayAdapter<Story> {

    public StoryAdapter(Activity context, ArrayList<Story> stories) {
        super(context, R.layout.list_item, stories);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Story currentStory = getItem(position);

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        TextView storyNameTextView = listItemView.findViewById(R.id.storyName);
        assert currentStory != null;
        storyNameTextView.setText(currentStory.getName());

        ImageView favoriteImageView = listItemView.findViewById(R.id.favoriteImage);
        if (!currentStory.isFavorite()) favoriteImageView.setVisibility(View.INVISIBLE);
        else favoriteImageView.setVisibility(View.VISIBLE);

        if (currentStory.isRead())
            listItemView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.read_story_color));
        else
            listItemView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.unread_story_color));

        return listItemView;
    }


}
