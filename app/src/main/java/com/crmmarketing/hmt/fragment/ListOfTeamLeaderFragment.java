package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crmmarketing.hmt.GsonModel.User;
import com.crmmarketing.hmt.GsonModel.UserDetail;
import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.adapter.TeamLeaderListAdapter;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.model.EmployeeInfo;
import com.crmmarketing.hmt.webservice.WSInquiry;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class ListOfTeamLeaderFragment extends Fragment implements SearchView.OnQueryTextListener {
    private Context context;
    private ProgressDialog progressDialog;
    private String id;
    private String role;
    private ArrayList<User> userArrayList;
    private RecyclerView recyclerView;
    private TeamLeaderListAdapter teamLeaderListAdapter;
    private ArrayList<EmployeeInfo> employeeInfoArrayList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        employeeInfoArrayList = new ArrayList<>();
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        role = sharedPref.getString("role", null);
        id = String.valueOf(sharedPref.getInt("id", 0));
        userArrayList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("Employee List");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_list_of_teamleader, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.e("view created", "ListOfTeamLead");
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_list_of_teamleader_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        new LoginCheckTask().execute();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "restore", ListOfTeamLeaderFragment.this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            getFragmentManager().getFragment(savedInstanceState, "restore");
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("hidden called", "TeamLead");
        ((HomeActivity) context).setTitle("Employee List");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);


        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
// Do something when collapsed
                        teamLeaderListAdapter.setFilter(userArrayList);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
// Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_employee) {
            //  addFragment(new AddEmployeeFragment(), ListOfTeamLeaderFragment.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<User> filteredModelList = filter(userArrayList, newText);

        if (teamLeaderListAdapter != null) {
            teamLeaderListAdapter.setFilter(filteredModelList);
        }

        return true;
    }

    private List<User> filter(List<User> models, String query) {
        query = query.toLowerCase().trim();
        final List<User> filteredModelList = new ArrayList<>();
        for (User model : models) {
            final String text = model.getUName().toLowerCase();

            final String empId = model.getUContact().toLowerCase();
            final String city = model.getUAddress().toLowerCase();
            if (text.contains(query) || empId.contains(query) || city.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


    private void addFragment(Fragment toAdd, Fragment current) {

        toAdd.setTargetFragment(current, 120);
        getFragmentManager().beginTransaction()
                .add(R.id.content_home, toAdd, toAdd.getClass().getSimpleName())
                .addToBackStack(null)
                .hide(current).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 120 && resultCode == Activity.RESULT_OK) {

            final EmployeeInfo employeeInfo = new EmployeeInfo();
            employeeInfo.setEmpName("Maya");
            employeeInfo.setEmpNo("108");
            employeeInfoArrayList.add(0, employeeInfo);
            teamLeaderListAdapter.notifyItemInserted(0);
            recyclerView.scrollToPosition(0);
            getActivity().onBackPressed();
        }
    }


    private class LoginCheckTask extends AsyncTask<String, Void, String> {

        private String url;
        private String response;

        LoginCheckTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url = getString(R.string.get_emp_list);
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                // response = new WSLogin(getActivity(),getResources().getString(R.string.login_url),username,password).postLoginData();
                // response=new WSLogin().postLoginData(getString(R.string.login_url),loginJSON(username,password));
                response = new WSInquiry().getEmpList(url, id, Constants.root);
                Log.e("Response", response);
            } catch (IOException e) {
                e.printStackTrace();
                progressDialog.dismiss();


            } catch (IllegalArgumentException e) {


            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            String isValid = null;
            String role = null;
            String username = null;

            if (getActivity() != null && progressDialog.isShowing()) {

                progressDialog.dismiss();
            }


            if (response != null) {

                UserDetail userDetail = new Gson().fromJson(response, UserDetail.class);
                userArrayList = (ArrayList<User>) userDetail.getUsers();

                for (User user : userArrayList) {
                    if (user.getUId().equals("1")) {
                        userArrayList.remove(user);
                        break;
                    }
                }


                if (userArrayList.size() > 0 && getActivity() != null) {

                    teamLeaderListAdapter = new TeamLeaderListAdapter(userArrayList, getActivity());
                    recyclerView.setAdapter(teamLeaderListAdapter);
                } else {
                    if (getActivity() != null) {

                        Toast.makeText(getActivity(), "No Data Available", Toast.LENGTH_LONG).show();
                    }
                }

            }


        }


        public void showProgressDialog() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Loading..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

    }

}
