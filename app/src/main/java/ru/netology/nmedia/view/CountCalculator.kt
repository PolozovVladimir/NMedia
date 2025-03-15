package ru.netology.nmedia.view

object CountCalculator {
    fun calculator(number: Int): String{
       return when{
            number < 1000 -> number.toString()
           number < 1100 -> "${number/1000}K"
           number < 10000 -> {
               val truncated: Int = number/100
               "%.1fk".format(truncated/10.0)
           }
           number < 1_000_000 -> "${number/1000}K"
           else -> {
               val truncated = number / 100_000
               val formatted = truncated / 10.0
               if (formatted % 1.0 == 0.0) {
                   "${formatted.toInt()}M"
               }else{
                   "%.1fm".format(formatted)
               }
           }
               }
           }
       }