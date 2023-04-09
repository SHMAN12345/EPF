package my.edu.tarc.epf.ui.investment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import my.edu.tarc.epf.R
import my.edu.tarc.epf.databinding.FragmentInvestmentBinding
import java.util.Calendar
import java.util.Calendar.*

/**
 * A simple [Fragment] subclass.
 * Use the [InvestmentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InvestmentFragment : Fragment() {

    private var _binding:FragmentInvestmentBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentInvestmentBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonDOB.setOnClickListener{
            val dateDialogFragment=DateDialogFragment{
                year, month, day ->  onDateSelected(year,month,day)

            }

            dateDialogFragment.show(parentFragmentManager,"DateDialog")
        }
        binding.buttonCalculate.setOnClickListener{
            val balance = binding.editTextBalanceAccount1.text.toString().toFloat()
            val age = binding.textViewAge.text.toString().toInt()
            var excess = 0.0

            val basic = when(age){
                in 16..20 -> 5000
                in 21..25 -> 14000
                in 26..30 -> 29000
                in 31..35 -> 50000
                in 36..40 -> 78000
                in 41..45 -> 116000
                in 46..50 -> 165000
                in 51..55 -> 228000
                else -> 0
            }

            excess = ((balance - basic)*0.3)

            binding.textViewAmountInvestment.text = excess.toString()
        }
        binding.buttonReset.setOnClickListener{ }
    }

    private fun onDateSelected(year: Int, month: Int, day: Int) {
        binding.buttonDOB.text=String.format("%02d/%02d/%d",day,month+1,year)
        val dob=getInstance()
        with(dob)
        {
            set(YEAR,year)
            set(MONTH,month)
            set(DAY_OF_MONTH,day)
        }

        val today= getInstance()
        val age=daysBetween(dob,today).div(365)
        binding.textViewAge.text=age.toString()
    }

    fun daysBetween(startDate:Calendar,endDate:Calendar):Long{
        var daysBetween:Long=0
        val date=startDate.clone() as Calendar
        while (date.before(endDate))
        {
            date.add(DAY_OF_MONTH,1)
            daysBetween++
        }

        return daysBetween
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding=null
    }

    class DateDialogFragment(val dateSetListener:(year:Int,month:Int,day:Int)->Unit):DialogFragment(),DatePickerDialog.OnDateSetListener
    {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c=Calendar.getInstance()
            val year=c.get(YEAR)
            val month=c.get(MONTH)
            val day=c.get(DAY_OF_MONTH)

            return DatePickerDialog(requireContext(),this,year,month,day)
        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            dateSetListener(year,month,dayOfMonth)
        }

    }
}