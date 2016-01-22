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


public class Home extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<Article> listArticles;
    private ProgressBar progressBar;
    public static LinearLayout progressLayout;
    LinearLayoutManager llm;
    private int visibleThreshold = 3;
    private int previousTotal=0;
    private int page=1;

    private boolean loading = true;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    public Home() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView= (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        llm=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        listArticles = new ArrayList<>();


        getData(page);
       /* adapter.setOnLoadMoreListener(new CardAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add progress item
                listArticles.add(null);
                adapter.notifyItemInserted(listArticles.size() - 1);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //remove progress item
                        listArticles.remove(listArticles.size() - 1);
                        adapter.notifyItemRemoved(listArticles.size());
                        getData(page);
                        ++page;
                        //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
                    }
                }, 2000);
                System.out.println("load");
            }
        });
    */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = llm.getItemCount();
                firstVisibleItem = llm.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    // Do something

                    page++;
                    listArticles.remove(null);
                    adapter.notifyDataSetChanged();
                    getData(page);

                    loading = true;

                }

            }
        });
        adapter=new CardAdapter(listArticles,recyclerView,getContext());
        recyclerView.setAdapter(adapter);
    }
    private void getData(int page){
        //Showing a progress dialog
        // final ProgressDialog loading = ProgressDialog.show(getActivity(),"Loading Data", "Please wait...",false,false);

        listArticles.add(null);
        //  adapter.notifyDataSetChanged();
        System.out.println("The articles size is " + listArticles.size());
        String url="https://tamilglitz.in/api/get_recent_posts/?count=2&page=";
        //Creating a json array request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url+String.valueOf(page),null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Dismissing progress dialog
                        //  loading.dismiss();


                        //  adapter.notifyDataSetChanged();
                        try {
                            JSONArray post = response.getJSONArray("posts");
                            for (int i = 0; i < post.length(); i++) {
                                JSONObject obj = post.getJSONObject(i);
                                Article topic = new Article();
                                topic.setTitle(obj.getString("title"));
                                topic.setDate(obj.getString("date"));
                                JSONObject thumbnailObject=obj.getJSONObject("thumbnail_images");
                                JSONObject authorObj =obj.getJSONObject("author");
                                JSONObject thumbnailfullobj=thumbnailObject.getJSONObject("full");
                                topic.setThumbUrl(thumbnailfullobj.getString("url"));
                                topic.setId(obj.getInt("id"));
                                topic.setAuthor(authorObj.getString("name"));
                                // topic.setDesc("Desc "+i);
                                //  topic.setTitle("Title "+i);
                                listArticles.add(topic);

                            }

                            System.out.println("The size now after fetch is "+listArticles.size());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // listArticles.remove(null);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getContext(),"network Timeout",
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

                            Toast.makeText(getContext(),"parese error",
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
