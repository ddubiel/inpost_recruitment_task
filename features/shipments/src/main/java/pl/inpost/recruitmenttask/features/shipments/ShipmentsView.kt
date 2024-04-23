package pl.inpost.recruitmenttask.features.shipments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import pl.inpost.recruitmenttask.data.network.model.ShipmentStatus
import pl.inpost.recruitmenttask.features.shipments.model.ShipmentModel
import pl.inpost.recruitmenttask.features.shipments.model.formatToStringDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShipmentsView(
    viewModel: ShipmentsViewModel = hiltViewModel()
) {
    val viewState = viewModel.uiState.collectAsState()
    val pullRefreshState =
        rememberPullRefreshState(viewState.value.isRefreshing, { viewModel.refresh() })
    val shipments = viewState.value.shipments
    val readyToPickupShipments = shipments.filter { it.status == ShipmentStatus.READY_TO_PICKUP }
    val otherPickupShipments = shipments.filter { it.status != ShipmentStatus.READY_TO_PICKUP }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            readyToPickupShipments
                .takeIf { it.isNotEmpty() }
                ?.sortedByDescending { it.operations.highlight }?.let { shipments ->
                item {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxSize(),
                        text = "Gotowe do odbioru",
                        textAlign = TextAlign.Center,
                        style = Typography.h1
                    )
                }
                items(count = shipments.size) {
                    ShipmentListItem(shipments[it]) { shipmentNumber ->
viewModel.archiveShipment(shipmentNumber)
                    }
                    if (shipments.size > it + 1) {
                        Spacer(modifier = Modifier.padding(16.dp))
                    }
                }
            }
            otherPickupShipments.takeIf { it.isNotEmpty() }?.let { shipments ->
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 16.dp),
                        text = "Pozostałe",
                        textAlign = TextAlign.Center,
                        style = Typography.h1
                    )
                }
                items(count = shipments.size) {
                    ShipmentListItem(shipments[it]) { shipmentNumber ->
                        viewModel.archiveShipment(shipmentNumber)
                    }
                    if (shipments.size > it + 1) {
                        Spacer(modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
        PullRefreshIndicator(
            viewState.value.isRefreshing,
            pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )

    }

}

@Composable
fun ShipmentListItem(shipmentModel: ShipmentModel, onMoreClicked: (String) -> Unit) {
    Surface(modifier = Modifier, elevation = 5.dp) {

        ConstraintLayout(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .fillMaxWidth()
        ) {
            val (packageNumber, icon, status, sender, more) = createRefs()
            Column(modifier = Modifier.constrainAs(packageNumber) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }) {
                Text(
                    text = stringResource(R.string.shipment_number),
                    color = Color(0xFF929497),
                    style = Typography.h2
                )
                Text(text = shipmentModel.number, color = Color(0xFF404041), style = Typography.h1)
            }
            Image(
                painter = painterResource(
                    if (shipmentModel.status == ShipmentStatus.READY_TO_PICKUP) {
                        R.drawable.paczkomat
                    } else R.drawable.kurier
                ),
                modifier = Modifier.constrainAs(icon) {
                    end.linkTo(parent.end)
                    top.linkTo(packageNumber.top)
                    bottom.linkTo(packageNumber.bottom)
                },
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )

            Row(modifier = Modifier.constrainAs(status) {
                top.linkTo(packageNumber.bottom, margin = 16.dp)
                start.linkTo(packageNumber.start)
                end.linkTo(icon.end)
            }) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.status),
                        color = Color(0xFF929497),
                        style = Typography.h2
                    )
                    Text(
                        text = stringResource(id = shipmentModel.status.nameRes),
                        color = Color(0xFF404041),
                        style = Typography.h3,
                        maxLines = 2
                    )
                }
                if (shipmentModel.status == ShipmentStatus.READY_TO_PICKUP) {
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                        Text(
                            text = stringResource(R.string.waiting_for_pickup),
                            color = Color(0xFF929497),
                            style = Typography.h2,
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = shipmentModel.expiryDate?.formatToStringDate().orEmpty(),
                            color = Color(0xFF404041),
                            style = Typography.h1,
                            textAlign = TextAlign.End
                        )

                    }
                }
            }


            Column(modifier = Modifier.constrainAs(sender) {
                top.linkTo(status.bottom, margin = 16.dp)
                start.linkTo(parent.start)
            }) {
                Text(
                    text = stringResource(R.string.sender),
                    color = Color(0xFF929497),
                    style = Typography.h2
                )
                Text(
                    text = shipmentModel.sender?.name.orEmpty(),
                    color = Color(0xFF404041),
                    style = Typography.h3
                )
            }

            Row(modifier = Modifier
                .constrainAs(more) {
                    bottom.linkTo(sender.bottom)
                    end.linkTo(parent.end)

                }
                .clickable {
                    onMoreClicked(shipmentModel.number)
                }) {
                Text(
                    text = stringResource(R.string.more),
                    color = Color(0xFF404041),
                    style = Typography.h3.copy(fontSize = 12.sp)
                )
                Image(
                    painter = painterResource(R.drawable.arrow),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}
