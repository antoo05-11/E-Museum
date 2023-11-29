package com.example.e_museum.fragments.fragments_thing_finding

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.e_museum.MainActivity
import com.example.e_museum.R
import com.example.e_museum.activities.ViewThingActivity
import com.example.e_museum.databinding.FragmentFindingThingByIdBinding
import com.example.e_museum.entities.Thing


class FindingThingByIDFragment : Fragment() {

    private var _binding: FragmentFindingThingByIdBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindingThingByIdBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val shake = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)

        binding.thingIdTv.text = ""
        val numButtons = arrayOf(
            binding.num0, binding.num1,
            binding.num2, binding.num3,
            binding.num4, binding.num5,
            binding.num6, binding.num7,
            binding.num8, binding.num9
        )

        for (i in 0..9) {
            numButtons[i].setOnClickListener {
                if (binding.thingIdTv.text.length > 10) return@setOnClickListener
                binding.thingIdTv.text =
                    String.format(binding.thingIdTv.text.toString() + numButtons[i].text.toString())
            }
        }

        binding.deleteNumButton.setOnClickListener {
            if (binding.thingIdTv.text.isNotEmpty()) {
                binding.thingIdTv.text =
                    binding.thingIdTv.text.substring(0, binding.thingIdTv.text.length - 1)
            }
        }

        binding.searchThingButton.setOnClickListener {
            Thread {
                val queryString = String.format(
                    "select * from things where thingID = %s", binding.thingIdTv.text
                )

                val resultSet = MainActivity.sqlConnection.getDataQuery(queryString)

                if (resultSet == null || !resultSet.isBeforeFirst) {
                    val vibrator: Vibrator? =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            val vibratorManager =
                                requireActivity().getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager?
                            vibratorManager!!.defaultVibrator
                        } else {
                            requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) vibrator!!.vibrate(
                            VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE)
                        ) else vibrator!!.vibrate(400)

                    requireActivity().runOnUiThread {
                        binding.thingIdTv.clearAnimation()
                        binding.thingIdTv.startAnimation(shake)
                    }
                    return@Thread
                }

                if (resultSet.next()) {
                    requireActivity().runOnUiThread {
                        val intent = Intent(requireContext(), ViewThingActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("thing", Thing(resultSet))
                        startActivity(intent)
                    }
                }
            }.start()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}