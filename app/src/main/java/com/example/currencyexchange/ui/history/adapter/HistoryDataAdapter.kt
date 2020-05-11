import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchange.R
import com.example.currencyexchange.network.fxhistory.model.FxHistoryData
import com.example.currencyexchange.utils.DataConstants
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.item_chart.view.*
import java.util.*


class HistoryDataAdapter(val context: Context) :
    RecyclerView.Adapter<HistoryDataAdapter.HistoryViewHolder>(
    ) {

    lateinit var lineDataSet: LineDataSet
    lateinit var fxHistoryData: FxHistoryData
    lateinit var chart: LineChart


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val historyView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chart, parent, false)

        return HistoryViewHolder(historyView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.itemView.currency_text_view.text = DataConstants.CURRENCY_LIST[position]
        chart = holder.itemView.chart
        createChart(chart)
        updateDataSet(fxHistoryData, position)
        chart.data = LineData(lineDataSet)
        chart.invalidate()
    }

    override fun getItemCount(): Int {
        if (::fxHistoryData.isInitialized)
            return fxHistoryData.rates.values.random().size
        return 0
    }

    class HistoryViewHolder(historyLayout: View) : RecyclerView.ViewHolder(historyLayout)


    private fun updateDataSet(fxHistoryData: FxHistoryData, position: Int) {
        lineDataSet = LineDataSet(getEntries(fxHistoryData, position), "")
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillDrawable = context.getDrawable(R.drawable.blue_to_white_gradient)
        lineDataSet.valueTextColor = Color.BLACK
        lineDataSet.valueTextSize = 12f
        lineDataSet.label = ""
        lineDataSet.setDrawValues(false)
    }

    private fun createChart(lineChart: LineChart) {
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.axisLeft.setDrawLabels(false)
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.setDrawLabels(false)
        lineChart.axisRight.setDrawGridLines(false)
        lineChart.legend.isEnabled = false
        lineChart.axisRight.setDrawLabels(false)
        lineChart.description.isEnabled = false
        lineChart.isDoubleTapToZoomEnabled = false
        lineChart.isDragEnabled = false
        lineChart.setDrawBorders(false)
    }

    private fun getEntries(fxHistoryData: FxHistoryData, position: Int): ArrayList<Entry> {
        val lineEntries = ArrayList<Entry>()
        var counter = 0f
        fxHistoryData.rates.values.forEach { currencyMap ->
            currencyMap.get(DataConstants.CURRENCY_LIST[position])?.let {
                counter++
                lineEntries.add(Entry(counter, it.toFloat()))
            }
        }
        Log.d("TAG", "$lineEntries ")
        return lineEntries
    }


}