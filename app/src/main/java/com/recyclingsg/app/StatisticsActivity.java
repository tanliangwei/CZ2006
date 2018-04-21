package com.recyclingsg.app;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.Math.round;

public class StatisticsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static final String TAG = "Statistics Activity";

    /**
     The BarChart is defined here
     */
    private static BarChart barChart;
    private static PieChart pieChart;
    private static BarChart topAverageUserPersonalChart;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private StatisticsManager statisticsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        statisticsManager = StatisticsManager.getInstance();
        // initialiseTheDateButtons();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * The function below loads all the statistics
     */
    public void loadAllStatistics(){
//        topUsers = statisticsManager.getTopUsers();
//        nationalStat = statisticsManager.getNationalStat();
//        userScore = statisticsManager.getUserScore();

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;

            int viewNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (viewNumber){
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_statistics1, container, false);
                    pieChart = (PieChart) rootView.findViewById(R.id.PieChart);
                    loadNationalView();
                    rootView.canScrollVertically(1);
                    rootView.canScrollVertically(-1);
                    return rootView;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_statistics2, container, false);
                    barChart = (BarChart) rootView.findViewById(R.id.BarChart);
                    loadTopUserView();
                    rootView.canScrollVertically(1);
                    rootView.canScrollVertically(-1);
                    return rootView;
                case 3:
                    rootView = inflater.inflate(R.layout.deposit_history_layout, container, false);
                    TableLayout depositHistoryTable = rootView.findViewById(R.id.DepositHistoryTable);

                    ArrayList<SimpleDepositLog> depositLogs = StatisticsManager.getInstance().getDepositLogs();
                    for(int i=depositLogs.size()-1; i>=0; i--){
                        SimpleDepositLog log = depositLogs.get(i);
                        View tableRowView = inflater.inflate(R.layout.deposit_record_view, depositHistoryTable, false);
                        tableRowView.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                        TextView dateView =  tableRowView.findViewById(R.id.record_date);
                        dateView.setText(log.getDepositDate());
                        TextView trashTypeView = tableRowView.findViewById(R.id.record_trash_type);
                        trashTypeView.setText(log.getTrashType());
                        TextView scoreView = tableRowView.findViewById(R.id.record_score_change);
                        scoreView.setText(String.valueOf(log.getScore()));

                        depositHistoryTable.addView(tableRowView, TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                    }
                    rootView.canScrollVertically(1);
                    rootView.canScrollVertically(-1);
                    return rootView;
            }
            return null;
        }

        /**
         * function for second view
         */
        private void loadTopUserView(){
            barChart.getDescription().setEnabled(false);
            barChart.setPinchZoom(false);

            barChart.setDrawBarShadow(false);
            barChart.setDrawGridBackground(false);
            barChart.animateY(2500);

            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            YAxis leftAxis = barChart.getAxisLeft();
            YAxis rightAxis = barChart.getAxisRight();
            rightAxis.setEnabled(false);


            barChart.getAxisLeft().setDrawGridLines(false);

            barChart.getLegend().setEnabled(false);

            final ArrayList<TopUser> topUsers = StatisticsManager.getInstance().getTopUsers();
            ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
            final ArrayList<String> xVals = new ArrayList<>();

            for(int i=0;i<topUsers.size();i++){
                TopUser user = topUsers.get(i);

                yVals.add(new BarEntry(i,(float)user.getScore()));
                xVals.add(user.getUserName());
            }

            double nationalAvgScore = StatisticsManager.getInstance().getNationalStat().getAvgScore();
            ArrayList<BarEntry> nationY = new ArrayList<>();

            nationY.add(new BarEntry(topUsers.size(), (float)nationalAvgScore));


            BarDataSet dataSet = new BarDataSet(yVals, "Top User Scores");
            BarDataSet nationAvgDataSet = new BarDataSet(nationY, "National Average");
            dataSet.setColor(ResourcesCompat.getColor(getResources(), R.color.navGreen, null));
            nationAvgDataSet.setColor(ResourcesCompat.getColor(getResources(), R.color.ligthBlue, null));
            ArrayList<IBarDataSet> bardataIB = new ArrayList<>();
            bardataIB.add(dataSet);
            bardataIB.add(nationAvgDataSet);
            BarData barData = new BarData(bardataIB);
            xAxis.setLabelCount(xVals.size()+1, false);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setLabelRotationAngle(-30f);
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    if (value>=topUsers.size())
                        return "National Average";
                    else {
                        return xVals.get(round(value));
                    }
                }
            });

            barChart.setData(barData);
            barChart.setFitBars(true);
        }

        /**
         * function for first view
         */
        private void loadNationalView(){

            pieChart.setUsePercentValues(true);
            pieChart.getDescription().setEnabled(false);
            pieChart.setExtraOffsets(5, 10, 5, 5);

            pieChart.setDragDecelerationFrictionCoef(0.95f);

            pieChart.setCenterText(generateCenterSpannableText());

            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleColor(Color.WHITE);

            pieChart.setTransparentCircleColor(Color.WHITE);
            pieChart.setTransparentCircleAlpha(110);

            pieChart.setHoleRadius(58f);
            pieChart.setTransparentCircleRadius(61f);

            pieChart.setDrawCenterText(true);

            pieChart.setRotationAngle(0);
            // enable rotation of the chart by touch
            pieChart.setRotationEnabled(true);
            pieChart.setHighlightPerTapEnabled(true);

            setNationalPercentageData();

            pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        }

        private void setNationalPercentageData() {
            ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

            entries.add(new PieEntry(StatisticsManager.getInstance().getNationalStat().getCashForTrashCount(), "Cash for Trash"));
            entries.add(new PieEntry(StatisticsManager.getInstance().getNationalStat().getEwastCount(),"E-waste"));
            entries.add(new PieEntry(StatisticsManager.getInstance().getNationalStat().getSecondHandGoodCount(),"2nd Hand Goods"));

            PieDataSet dataSet = new PieDataSet(entries, "Trash Type");

            dataSet.setDrawIcons(false);

            dataSet.setSliceSpace(3f);
            dataSet.setIconsOffset(new MPPointF(0, 40));
            dataSet.setSelectionShift(5f);

            // add a lot of colors

            ArrayList<Integer> colors = new ArrayList<Integer>();

            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);

            colors.add(ColorTemplate.getHoloBlue());

            dataSet.setColors(colors);

            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.BLACK);
            pieChart.setData(data);
            pieChart.setEntryLabelColor(Color.BLACK);

            // undo all highlights
            pieChart.highlightValues(null);

            pieChart.invalidate();
        }

        private SpannableString generateCenterSpannableText() {

            SpannableString s = new SpannableString("National Trash Pool");
            s.setSpan(new RelativeSizeSpan(1.7f), 0, s.length(), 0);
            s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
            return s;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

}

