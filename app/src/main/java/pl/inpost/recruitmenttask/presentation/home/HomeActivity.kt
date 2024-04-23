package pl.inpost.recruitmenttask.presentation.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.features.shipments.ShipmentListItem
import pl.inpost.recruitmenttask.features.shipments.ShipmentsView
import pl.inpost.recruitmenttask.presentation.shipmentList.ShipmentListFragment
import pl.inpost.recruitmenttask.ui.theme.InpostTheme

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            InpostTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShipmentsView()
                }
            }
        }
    }
}
