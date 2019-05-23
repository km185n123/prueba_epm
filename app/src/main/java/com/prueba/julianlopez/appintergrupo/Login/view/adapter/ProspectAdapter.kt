package com.prueba.julianlopez.appintergrupo.Login.view.adapter

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.Log
import com.prueba.julianlopez.appintergrupo.Login.bussines.model.Prospect
import com.prueba.julianlopez.appintergrupo.Login.Util.ConstantsMessages
import com.prueba.julianlopez.appintergrupo.Login.Util.DataCache
import com.prueba.julianlopez.appintergrupo.Login.Util.ExpandAndCollapseViewUtil
import com.prueba.julianlopez.appintergrupo.Login.view.ui.DashBoardActivity
import com.prueba.julianlopez.appintergrupo.Login.viewmodel.CardProspectViewModel
import com.prueba.julianlopez.appintergrupo.R
import kotlinx.android.synthetic.main.prospect_row.view.*
import java.util.*


class ProspectAdapter (private var context: Context, private var list: List<Prospect>): RecyclerView.Adapter<ProspectAdapter.CustomViewHolder>() {


    private var cardProspectViewModel : CardProspectViewModel? = null
    init {
        cardProspectViewModel = ViewModelProviders.of(context as DashBoardActivity).get(CardProspectViewModel::class.java!!)

    }

    private var swichChangeIconBtnExpander = true
    private val DURATION = 250
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.prospect_row, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val item = list!![position]

        holder.name!!.text = item.name+" "+item.surname
        holder.document!!.text = item.schProspectIdentification
        holder.phone!!.text = item.telephone
        holder.statusCode!!.text = item.statusCd.toString()
        holder.txtname!!.setText(item.name)
        holder.txtLastName!!.setText(item.surname)
        holder.txtPhone!!.setText(item.telephone)
        holder.txtAddress!!.setText(item.address)
        holder.txtObservation!!.setText(item.observation)


        holder.btnExpander!!.setOnClickListener {

            if (swichChangeIconBtnExpander ) {
                holder.iconBtnExpander!!.setBackground(context.getResources().getDrawable(R.drawable.ic_arrow_up,null))
                ExpandAndCollapseViewUtil.expand(holder.infoUserData, DURATION)
                swichChangeIconBtnExpander = false
            } else {
                holder.iconBtnExpander!!.setBackground(context.getResources().getDrawable(R.drawable.ic_arrow_down,null))
                ExpandAndCollapseViewUtil.collapse(holder.infoUserData, DURATION)
                swichChangeIconBtnExpander = true
            }
        }


        holder.btnUpdate!!.setOnClickListener {

            validateForm(holder,item)
        }

        when (item.statusCd) {
            0 -> holder.colorStatus!!.background = context.resources.getDrawable(R.color.yellow,null)
            1 -> holder.colorStatus!!.background = context.resources.getDrawable(R.color.red,null)
            2 -> holder.colorStatus!!.background = context.resources.getDrawable(R.color.green,null)
            3 -> holder.colorStatus!!.background = context.resources.getDrawable(R.color.gray,null)
            4 -> holder.colorStatus!!.background = context.resources.getDrawable(R.color.colorPrimary,null)

            else -> { // Note the block
                holder.colorStatus!!.background = context.resources.getDrawable(R.color.white,null)
            }
        }


    }

    private fun validateForm(holder: CustomViewHolder, item: Prospect) {



        when(true){
            holder.txtname!!.text.isNullOrEmpty() ->holder.txtname!!.error = ConstantsMessages.OBLIGATORY_FIELD
            holder.txtLastName!!.text.isNullOrEmpty() ->holder.txtLastName!!.error = ConstantsMessages.OBLIGATORY_FIELD
            holder.txtPhone!!.text.isNullOrEmpty() ->holder.txtPhone!!.error = ConstantsMessages.OBLIGATORY_FIELD
            holder.txtAddress!!.text.isNullOrEmpty() ->holder.txtAddress!!.error = ConstantsMessages.OBLIGATORY_FIELD
            holder.txtObservation!!.text.isNullOrEmpty() ->holder.txtObservation!!.error = ConstantsMessages.OBLIGATORY_FIELD
            else-> {

                var log = Log()

                savePreviousDataInLog(log,item)

                saveNewDataInDataBase(item,holder)


                syncUpDataLogTables(log,item)


                cardProspectViewModel!!.saveNewLog(log)


                notifyDataSetChanged()
            }


        }



    }

    private fun saveNewDataInDataBase(item: Prospect, holder: CustomViewHolder) {

        item.name = holder.txtname!!.text.toString()
        item.surname = holder.txtLastName!!.text.toString()
        item.telephone = holder.txtPhone!!.text.toString()
        item.address = holder.txtAddress!!.text.toString()
        item.observation = holder.txtObservation!!.text.toString()
        item.updated = 1


        cardProspectViewModel?.updateProspect(item)
    }

    private fun savePreviousDataInLog(log: Log, item: Prospect) {

        log.previousName = item.name
        log.previousSurname = item.surname
        log.previousTelephone = item.telephone
        log.previousAddress = item.address
        log.previousRejectedObservation = item.rejectedObservation
        log.previousSchProspectIdentification = item.schProspectIdentification
    }


    override fun getItemCount(): Int {
        return list?.size
    }

    override fun getItemViewType(position: Int): Int {
        return position;
    }

    inner class CustomViewHolder(view: View): RecyclerView.ViewHolder(view){

        var name: TextView? = view.name
        var document: TextView? = view.document
        var phone: TextView? = view.phone
        var statusCode: TextView? = view.statusCode
        var colorStatus: LinearLayout? = view.colorStatus
        var btnExpander: LinearLayout? = view.btnExpander
        var infoUserData: LinearLayout? = view.infoUserData
        var iconBtnExpander: ImageView? = view.iconBtnExpander
        var btnUpdate: Button? = view.btnUpdate
        var txtname: AutoCompleteTextView? = view.txtname
        var txtLastName: AutoCompleteTextView? = view.txtLastName
        var txtPhone: AutoCompleteTextView? = view.txtPhone
        var txtAddress: AutoCompleteTextView? = view.txtAddress
        var txtObservation: AutoCompleteTextView? = view.txtObservation
    }


    private fun syncUpDataLogTables(log: Log, prospect: Prospect) {


        val c = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)

        log. name= prospect.name
        log. surname= prospect.surname
        log. telephone= prospect.telephone
        log. schProspectIdentification= prospect.schProspectIdentification
        log. address= prospect.address
        log. createdAt= prospect.createdAt
        log. updatedAt= prospect.updatedAt
        log. statusCd= prospect.statusCd
        log. zoneCode= prospect.zoneCode
        log. neighborhoodCode= prospect.neighborhoodCode
        log. cityCode= prospect.cityCode
        log. sectionCode= prospect.sectionCode
        log. roleId= prospect.roleId
        log. appointableId= prospect.appointableId
        log. rejectedObservation= prospect.rejectedObservation
        log. observation= prospect.observation
        log. isDisable= prospect.isDisable
        log. isVisited= prospect.isVisited
        log. isCallcenter= prospect.isCallcenter
        log. isAcceptSearch= prospect.isAcceptSearch
        log. campaignCode= prospect.campaignCode
        log. userId= prospect.userId
        log. userThatModifies= DataCache.readData(context,"EMAIL")

        log. date= c.getTime().toString();



    }





}