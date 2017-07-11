package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int)
    : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int =
        when { // `when` version stolen from solutions (`resolutions` branch)
            year != other.year -> year.compareTo(other.year)
            month != other.month -> month.compareTo(other.month)
            else -> dayOfMonth.compareTo(other.dayOfMonth)
        }
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

data class MultipleTimeInterval(val interval : TimeInterval, val times: Int)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> =
        object : Iterator<MyDate> {
            var next = start
            override fun hasNext(): Boolean = next<=endInclusive
            override fun next(): MyDate {
                var current = next
                next = next.nextDay()
                return current
            }
        }
}

operator fun TimeInterval.times(i: Int): MultipleTimeInterval = MultipleTimeInterval(this, i)
operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)
operator fun MyDate.plus(interval: TimeInterval): MyDate = this + interval.times(1)
operator fun MyDate.plus(x: MultipleTimeInterval): MyDate = this.addTimeIntervals(x.interval, x.times)
operator fun DateRange.contains(d:MyDate) = this.start <= d && this.endInclusive >= d

