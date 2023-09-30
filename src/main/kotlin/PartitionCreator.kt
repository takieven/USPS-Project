package partitionSetCreator

class PartitionSetCreator<T>(//the base set
    private val base: Set<T>
) {
    private val parts //the partitions that are created
            : MutableSet<Set<Set<T>>>
    private val pow //the power set of the input set
            : MutableSet<Set<T>>

    init {
        pow = powerSet(base)
        if (pow.size > 1) {
            //remove the empty set if it's not the only entry in the power set
            pow.remove(HashSet())
        }
        parts = HashSet()
    }

    /**
     * Calculation is in this method.
     */
    fun findAllPartitions(): Set<Set<Set<T>>> {
        //find part sets for every entry in the power set
        for (set in pow) {
            val current: MutableSet<Set<T>> = HashSet()
            current.add(set)
            findPartSets(current)
        }

        //return all partitions that were found
        return parts
    }

    /**
     * Finds all partition sets for the given input and adds them to parts (global variable).
     */
    private fun findPartSets(current: Set<Set<T>>) {
        val maxLen = base.size - deepSize(current)
        if (maxLen == 0) {
            //the current partition is full -> add it to parts
            parts.add(current)
            //no more can be added to current -> stop the recursion
            return
        } else {
            //for all possible lengths
            for (i in 1..maxLen) {
                //for every entry in the power set
                for (set in pow) {
                    if (set.size == i) {
                        //the set from the power set has the searched length
                        if (!anyInDeepSet(set, current)) {
                            //none of set is in current
                            val next: MutableSet<Set<T>> = HashSet()
                            next.addAll(current)
                            next.add(set)
                            //next = current + set
                            findPartSets(next)
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates a power set from the base set.
     */
    private fun powerSet(base: Set<T>): MutableSet<Set<T>> {
        val pset: MutableSet<Set<T>> = HashSet()
        if (base.isEmpty()) {
            pset.add(HashSet())
            return pset
        }
        val list: List<T> = ArrayList(base)
        val head = list[0]
        val rest: Set<T> = HashSet(list.subList(1, list.size))
        for (set in powerSet(rest)) {
            val newSet: MutableSet<T> = HashSet()
            newSet.add(head)
            newSet.addAll(set)
            pset.add(newSet)
            pset.add(set)
        }
        return pset
    }

    /**
     * The summed up size of all sub-sets
     */
    private fun deepSize(set: Set<Set<T>>): Int {
        var deepSize = 0
        for (s in set) {
            deepSize += s.size
        }
        return deepSize
    }

    /**
     * Checks whether any of set is in any of the sub-sets of current
     */
    private fun anyInDeepSet(set: Set<T>, current: Set<Set<T>>): Boolean {
        var containing = false
        for (s in current) {
            for (item in set) {
                containing = containing or s.contains(item)
            }
        }
        return containing
    }
}
