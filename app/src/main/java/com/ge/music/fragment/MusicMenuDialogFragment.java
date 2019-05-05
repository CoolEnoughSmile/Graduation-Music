package com.ge.music.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ge.music.R;
import com.ge.music.activity.MainActivity;
import com.ge.music.adapter.MusicMenuAdapter;
import com.ge.music.model.MusicModel;

public class MusicMenuDialogFragment extends BottomSheetDialogFragment {

    private MusicMenuAdapter menuAdapter;
    private MainActivity mainActivity;
    private RecyclerView recyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_music_menu,container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        menuAdapter = new MusicMenuAdapter(mainActivity.getList());
        menuAdapter.setOnItemClickListener((adapter,v,position) -> {
            mainActivity.play((MusicModel) adapter.getItem(position));
            MusicMenuDialogFragment.this.dismiss();
        });
        menuAdapter.setOnItemChildClickListener((adapter,v,position) -> {
            mainActivity.getList().remove(position);
            adapter.notifyDataSetChanged();
        });
        recyclerView.setAdapter(menuAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        menuAdapter.notifyDataSetChanged();
    }
}
