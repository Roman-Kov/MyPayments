package com.rojer_ko.mypayments.presentation.ui

import com.rojer_ko.mypayments.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_payments.*

class PaymentContainer(
    private val description: String,
    private val amount: String,
    private val currency: String,
    private val createdDate: String
) : Item() {

    override fun getLayout() = R.layout.item_payments

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            payment_description.text = description
            val amountCurrency = "$amount $currency"
            payment_amount_currency.text = amountCurrency
            payment_date.text = createdDate
        }
    }
}