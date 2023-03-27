package my.edu.tarc.epf

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import my.edu.tarc.epf.databinding.ActivityMainBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_dividend,R.id.nav_investment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //back press
        val backPressedCallback= object : OnBackPressedCallback(true)
        {
            override fun handleOnBackPressed() {
                val builder=AlertDialog.Builder(this@MainActivity)
                builder.setMessage("Are you sure want to exit?")
                    .setPositiveButton("Exit",{_,_-> finish()})
                    .setNegativeButton("Cancel",{_,_->})

                builder.create().show()
            }

        }



        //Navigate to profile
        //Index 0 = Profile Picture
        val view=navView.getHeaderView(0)
        val profilePic=view.findViewById<ImageView>(R.id.imageViewProfilePicture)
        val textViewName=view.findViewById<TextView>(R.id.textViewName)
        val textViewEmail=view.findViewById<TextView>(R.id.textViewEmail)


        view.setOnClickListener{
            findNavController(R.id.nav_host_fragment_content_main)
                .navigate(R.id.nav_profile)

            binding.drawerLayout.closeDrawers()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.action_settings)
        {
            Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show()
        }

        else if(item.itemId==R.id.action_about)
        {
            //Implement find navigation controller here
            findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.nav_about)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}