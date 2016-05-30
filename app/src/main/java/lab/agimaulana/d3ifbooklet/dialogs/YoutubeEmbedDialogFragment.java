package lab.agimaulana.d3ifbooklet.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;


import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import lab.agimaulana.d3ifbooklet.R;

/**
 * Created by root on 5/30/16.
 */
public class YoutubeEmbedDialogFragment extends AppCompatDialogFragment implements YouTubePlayer.OnInitializedListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.YoutubeDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().getAttributes().gravity = Gravity.TOP;
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, R.dimen.youtube_player_height);
        return inflater.inflate(R.layout.dialog_youtube_embed, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog() != null)
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, R.dimen.youtube_player_height);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        YouTubePlayerSupportFragment youtubePlayer = new YouTubePlayerSupportFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, youtubePlayer).commit();
        youtubePlayer.initialize(getString(R.string.YOUTUBE_KEY), this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.cueVideo("nCgQDjiotG0");
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        provider.initialize(getString(R.string.YOUTUBE_KEY), this);
    }
}
