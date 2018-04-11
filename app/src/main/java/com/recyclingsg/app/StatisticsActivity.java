package com.recyclingsg.app;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
     * the date selectors are defined here
     */
    private TextView fromDate;
    private TextView toDate;

    /**
     * caching all the data here
     */
    private static ArrayList<TopUser> topUsers = null;
    private static NationalStat nationalStat = null;
    private static double userScore = -1;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        loadAllStatistics();
        initialiseTheDateButtons();
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void initialiseTheDateButtons(){
        fromDate = (TextView)findViewById(R.id.fromDate);
        toDate = (TextView) findViewById(R.id.toDate);
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCurrentDate;
                mCurrentDate = Calendar.getInstance();
                int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
                int month = mCurrentDate.get(Calendar.MONTH);
                int year= mCurrentDate.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(StatisticsActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        monthOfYear = monthOfYear+1;
                        fromDate.setText(dayOfMonth+"/"+monthOfYear+"/"+year);

                    }
                }, year,month,day);
                datePickerDialog.show();
            }
        });
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCurrentDate;
                mCurrentDate = Calendar.getInstance();
                int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
                int month = mCurrentDate.get(Calendar.MONTH);
                int year= mCurrentDate.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(StatisticsActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        monthOfYear = monthOfYear+1;
                        toDate.setText(dayOfMonth+"/"+monthOfYear+"/"+year);

                    }
                }, year,month,day);
                datePickerDialog.show();
            }
        });
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
        StatisticsManager.getInstance();
        topUsers = StatisticsManager.getTopUsers();
        nationalStat = StatisticsManager.getNationalStat();
        userScore = StatisticsManager.getUserScore();

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
            TextView textView;
            int viewNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            switch (viewNumber){
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_statistics1, container, false);
                    // textView = (TextView) rootView.findViewById(R.id.section_label);
                    // textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                    pieChart = (PieChart) rootView.findViewById(R.id.PieChart);
                    loadNationalView();
                    return rootView;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_statistics2, container, false);
                    // textView = (TextView) rootView.findViewById(R.id.section_label);
                    // textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                    barChart = (BarChart) rootView.findViewById(R.id.BarChart);
                    loadTopUserView();
                    return rootView;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_statistics3, container, false);
                    topAverageUserPersonalChart = (BarChart) rootView.findViewById(R.id.BarChart2);
                    loadTopUserPersonalAverageView();
                    return rootView;
                default:
                    break;
            }
            return null;
        }

        /**
         * new function to load
         */

        private void loadTopUserPersonalAverageView(){
            float barWidth;
            float barSpace;
            float groupSpace;

            barWidth = 0.3f;
            barSpace = 0f;
            groupSpace = 0.4f;
            topAverageUserPersonalChart.setDescription(null);
            topAverageUserPersonalChart.setPinchZoom(false);
            topAverageUserPersonalChart.setScaleEnabled(false);
            topAverageUserPersonalChart.setDrawBarShadow(false);
            topAverageUserPersonalChart.setDrawGridBackground(false);

            int groupCount = 6;

            ArrayList xVals = new ArrayList();

            xVals.add("Jan");
            xVals.add("Feb");
            xVals.add("Mar");
            xVals.add("Apr");
            xVals.add("May");
            xVals.add("Jun");

            ArrayList yVals1 = new ArrayList();
            ArrayList yVals2 = new ArrayList();

            yVals1.add(new BarEntry(1, (float) 1));
            yVals2.add(new BarEntry(1, (float) 2));
            yVals1.add(new BarEntry(2, (float) 3));
            yVals2.add(new BarEntry(2, (float) 4));
            yVals1.add(new BarEntry(3, (float) 5));
            yVals2.add(new BarEntry(3, (float) 6));
            yVals1.add(new BarEntry(4, (float) 7));
            yVals2.add(new BarEntry(4, (float) 8));
            yVals1.add(new BarEntry(5, (float) 9));
            yVals2.add(new BarEntry(5, (float) 10));
            yVals1.add(new BarEntry(6, (float) 11));
            yVals2.add(new BarEntry(6, (float) 12));

            BarDataSet set1, set2;
            set1 = new BarDataSet(yVals1, "A");
            set1.setColor(Color.RED);
            set2 = new BarDataSet(yVals2, "B");
            set2.setColor(Color.BLUE);
            BarData data = new BarData(set1, set2);
            data.setValueFormatter(new LargeValueFormatter());
            topAverageUserPersonalChart.setData(data);
            topAverageUserPersonalChart.getBarData().setBarWidth(barWidth);
            topAverageUserPersonalChart.getXAxis().setAxisMinimum(0);
            topAverageUserPersonalChart.getXAxis().setAxisMaximum(0 + topAverageUserPersonalChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
            topAverageUserPersonalChart.groupBars(0, groupSpace, barSpace);
            topAverageUserPersonalChart.getData().setHighlightEnabled(false);
            topAverageUserPersonalChart.animateY(2500);
            topAverageUserPersonalChart.invalidate();

            Legend l = topAverageUserPersonalChart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(true);
            l.setYOffset(0f);
            l.setXOffset(0f);
            l.setYEntrySpace(0f);
            l.setTextSize(8f);

            //X-axis
            XAxis xAxis = topAverageUserPersonalChart.getXAxis();
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            xAxis.setCenterAxisLabels(true);
            xAxis.setDrawGridLines(false);
            xAxis.setAxisMaximum(6);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
            //Y-axis
            topAverageUserPersonalChart.getAxisRight().setEnabled(false);
            YAxis leftAxis = topAverageUserPersonalChart.getAxisLeft();
            leftAxis.setValueFormatter(new LargeValueFormatter());
            leftAxis.setDrawGridLines(true);
            leftAxis.setSpaceTop(35f);
            leftAxis.setAxisMinimum(0f);
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

            ArrayList<TopUser> topUsers = StatisticsManager.getTopUsers();
            ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
            final ArrayList<String> xVals = new ArrayList<>();

            for(int i=0;i<topUsers.size();i++){
                TopUser user = topUsers.get(i);

                yVals.add(new BarEntry(i,(float)user.getScore()));
                xVals.add(user.getUserName());
                // barentries.add(new barentry(i, i*10));
            }



            BarDataSet dataSet = new BarDataSet(yVals, "Top User Scores");
            ArrayList<IBarDataSet> bardataIB = new ArrayList<>();
            bardataIB.add(dataSet);
            BarData barData = new BarData(bardataIB);
            xAxis.setLabelCount(xVals.size() - 1, false);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return xVals.get((int)value);
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

            // pieChart.setUnit(" €");
            // pieChart.setDrawUnitsInChart(true);

            // add a selection listener
            //pieChart.setOnChartValueSelectedListener(this);

            setData(3, 100);

            pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        }

        private void setData(int count, float range) {

            float mult = range;

            ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

            // NOTE: The order of the entries when being added to the entries array determines their position around the center of
            // the chart.
            for (int i = 0; i < count ; i++) {
                entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
                        TrashInfo.typeOfTrash[i]));
            }

            PieDataSet dataSet = new PieDataSet(entries, "Trash BreakDown");

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
            //dataSet.setSelectionShift(0f);

            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.WHITE);
            pieChart.setData(data);

            // undo all highlights
            pieChart.highlightValues(null);

            pieChart.invalidate();
        }

        private SpannableString generateCenterSpannableText() {

            SpannableString s = new SpannableString("PieChart on Trash");
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

