package com.example.app.trabalhofinal_worldwidepublicholiday.View

import android.content.res.TypedArray
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.StyleableRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.app.trabalhofinal_worldwidepublicholiday.Network.Holiday
import com.example.app.trabalhofinal_worldwidepublicholiday.R
import com.example.app.trabalhofinal_worldwidepublicholiday.RestApiStatus
import com.example.app.trabalhofinal_worldwidepublicholiday.RetrofitViewModel
import com.example.app.trabalhofinal_worldwidepublicholiday.Util.LinkedHashMapAdapter
import com.example.app.trabalhofinal_worldwidepublicholiday.databinding.FragmentYearCountryBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

class YearCountryFragment : Fragment() {
    private val viewModel: RetrofitViewModel by activityViewModels();
    private lateinit var binding: FragmentYearCountryBinding;
    private val countryBrSpinnerPosition: Int = 13;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_year_country, container, false);
        binding.yearCountryViewModel = viewModel;
        binding.lifecycleOwner = this;

        fillSpinnerYear();
        fillSpinnerCountry();

        val holidays: ArrayList<Holiday> = ArrayList();
        binding.spinnerCountry.setSelection(countryBrSpinnerPosition);

        binding.buttonBuscar.setOnClickListener {
            val year = binding.spinnerYear.selectedItem.toString();
            val countryCode = binding.spinnerCountry.selectedItem.toString().split("=")[0];

            viewModel.getByYearAndCountryCode(year, countryCode);
        }

        viewModel.response.observe(viewLifecycleOwner, Observer { it ->
            holidays.clear();
            it?.forEach {
                holidays.add(Holiday(it.date, it.localName, it.name, it.countryCode, it.fixed, it.global, it.counties, it.launchYear, it.types));
            }
        });

        viewModel.status.observe(viewLifecycleOwner, Observer { it ->
            if (it != null) {
                binding.buttonBuscar.isEnabled = false;
                when (it) {
                    RestApiStatus.LOADING -> {
                        binding.textViewStatusRequisitionAPI.text = getString(R.string.loading_holidays);
                    }
                    RestApiStatus.ERROR -> {
                        binding.textViewStatusRequisitionAPI.text = getString(R.string.error_search_holidays);
                    }
                    RestApiStatus.DONE -> {
                        if (!holidays.isNullOrEmpty()) {
                            binding.textViewStatusRequisitionAPI.text = "";

                            val year = binding.spinnerYear.selectedItem.toString();
                            findNavController().navigate(YearCountryFragmentDirections.actionYearCountryFragmentToCalendarFragment(holidays.toTypedArray(), year.toInt()));
                        } else {
                            binding.textViewStatusRequisitionAPI.text = getString(R.string.error_show_holidays);
                        }
                    }
                }
                binding.buttonBuscar.isEnabled = true;
            }
        });

        return binding.root;
    }

    private fun fillSpinnerYear() {
        val arraySpiner: MutableList<String> = ArrayList();
        for (i in 1..9999) {
            arraySpiner.add(i.toString());
        }

        val adaptadorSpinner = ArrayAdapter(requireContext().applicationContext, android.R.layout.simple_spinner_dropdown_item, arraySpiner);
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerYear.adapter = adaptadorSpinner;
        binding.spinnerYear.setSelection(Calendar.getInstance().get(Calendar.YEAR) - 1);
    }

    private fun fillSpinnerCountry() {
        val countriesArrayOfArrays = resources.obtainTypedArray(R.array.countries_array_of_arrays);
        val size = countriesArrayOfArrays.length();
        val mapData: LinkedHashMap<String, String> = LinkedHashMap();
        var countryTypedArray: TypedArray? = null;

        @StyleableRes val firstPos = 0;
        @StyleableRes val secondPos = 1;
        for (i in 0 until size) {
            val id = countriesArrayOfArrays.getResourceId(i, -1)
            check(id != -1) {
                Toast.makeText(activity, getString(R.string.error_load_countries), Toast.LENGTH_LONG).show();
            }
            countryTypedArray = resources.obtainTypedArray(id);
            mapData[countryTypedArray.getString(firstPos).toString()] = countryTypedArray.getString(secondPos).toString();
        }
        countryTypedArray?.recycle();
        countriesArrayOfArrays.recycle();

        val adaptadorSpinner: LinkedHashMapAdapter<String, String> = LinkedHashMapAdapter(requireContext(), android.R.layout.simple_spinner_item, mapData);
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCountry.adapter = adaptadorSpinner;
    }
}