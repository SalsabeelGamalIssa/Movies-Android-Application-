package com.bolla.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bolla.myapplication.Activities.HomeActivity;
import com.bolla.myapplication.Classes.DownloadImageTask;
import com.bolla.myapplication.models.MovieModel;
import com.bolla.myapplication.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class HomeRvAdapter extends RecyclerView.Adapter<HomeRvAdapter.UserViewHolder> {
    private Context mContext;
    private List<MovieModel> movieModelList;
    MovieModel githubMovieModel;

    public HomeRvAdapter(Context mContext, List<MovieModel> movieModelList) {
        this.mContext = mContext;
        this.movieModelList = movieModelList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.home_rv_item, parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {
        githubMovieModel = movieModelList.get(position);
        holder.movieName.setText(githubMovieModel.getMovieName());
        holder.movieDirector.setText(githubMovieModel.getMovieDirector());
        holder.movieYear.setText(githubMovieModel.getYear());
        Glide.with(mContext).load(githubMovieModel.getMovieImage()).into(holder.movieImage);

//        new DownloadImageTask((ImageView) holder.movieImage)
//                .execute(githubMovieModel.getMovieImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                githubMovieModel = movieModelList.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(githubMovieModel.getMovieName()));
                mContext.startActivity(intent);
                Toast.makeText(mContext, "Item is clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieModelList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView movieName;
        TextView movieDirector;
        TextView movieYear;
        ImageView movieImage;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName=itemView.findViewById(R.id.tv_movie_name);
            movieDirector=itemView.findViewById(R.id.tv_movie_director);
            movieName=itemView.findViewById(R.id.tv_movie_name);
            movieYear=itemView.findViewById(R.id.tv_movie_year);
            movieImage=itemView.findViewById(R.id.iv_mov_image);
        }
    }
}
