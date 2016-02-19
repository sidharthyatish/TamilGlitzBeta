package com.tamilglitz.sidharthyatish.tamilglitzbeta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MovieTrailers extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CardAdapter adapter;
    private List<Article> listArticles;
    private ProgressBar progressBar;
    public static LinearLayout progressLayout;
    LinearLayoutManager llm;
    private int visibleThreshold = 3;
    private int previousTotal=0;
    private int page=1;

    private boolean loading = true;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    public MovieTrailers() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_trailers, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView= (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        llm=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        listArticles = new ArrayList<>();

        listArticles.add(null);
        getData(page);
        adapter=new CardAdapter(listArticles,recyclerView,getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new CardAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                listArticles.add(null);
                adapter.notifyItemInserted(listArticles.size() - 1);
                page++;
                getData(page);
            }
        });


    }
    private void getData(final int page){

        System.out.println("The articles size is " + listArticles.size());
        String url="https://tamilglitz.in/api/get_category_posts/?slug=movie-trailers&count=2&page=";
        //Creating a json array request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url+String.valueOf(page),null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        listArticles.remove(null);
                        adapter.notifyItemRemoved(listArticles.size());
                        try {
                            JSONArray post = response.getJSONArray("posts");

                            for (int i = 0; i < post.length(); i++) {
                                JSONObject obj = post.getJSONObject(i);
                                Article topic = new Article();
                                topic.setTitle(obj.getString("title"));

                                topic.setDate(obj.getString("date"));
                                topic.setContent(obj.getString("content"));
                                topic.setUrl(obj.getString("url"));

                                JSONArray categories=obj.getJSONArray("categories");
                                JSONObject categoryObj=categories.getJSONObject(0);
                                String content=categoryObj.getString("slug");
                                JSONObject thumbnailObject=obj.getJSONObject("thumbnail_images");
                                JSONObject authorObj =obj.getJSONObject("author");
                                JSONObject thumbnailfullobj=thumbnailObject.getJSONObject("full");
                                topic.setThumbUrl(thumbnailfullobj.getString("url"));
                                if(content.equals("movie-trailers")||content.equals("videos")||content.equals("video-songs")){
                                    JSONObject customFieldsObject=obj.getJSONObject("custom_fields");
                                    JSONArray tdPostVideo =customFieldsObject.getJSONArray("td_post_video");
                                    topic.setAttachmentUrl(tdPostVideo.getString(0));
                                }

                                topic.setId(obj.getInt("id"));
                                topic.setAuthor(authorObj.getString("name"));
                                // topic.setDesc("Desc "+i);
                                //  topic.setTitle("Title "+i);
                                listArticles.add(topic);
                                adapter.notifyItemInserted(listArticles.size());

                            }


                            System.out.println("The size now after fetch is "+listArticles.size());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // listArticles.remove(null);
                        // adapter.notifyDataSetChanged();
                        adapter.setLoaded();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getContext(), "network Timeout",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getContext(),"auth failure",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getContext(),"server error",
                                    Toast.LENGTH_LONG).show();

                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getContext(),"network error",Toast.LENGTH_LONG).show();

                        } else if (error instanceof ParseError) {

                            Toast.makeText(getContext(),"parse error",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //Adding request to the queue
        requestQueue.add(jsonObjectRequest);



    }

}
