class SkipIterator implements Iterator<Integer> {
    
    private final Iterator<Integer> it;
    private final Map<Integer, Integer> count;
    private Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.count = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new RuntimeException("No more elements");
        }
        Integer el = nextEl;
        advance();
        return el;
    }

    public void skip(int num) {
        if (!hasNext()) {
            throw new RuntimeException("No more elements");
        }
        if (nextEl.equals(num)) {
            advance();
        } else {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }
    }

    private void advance() {
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (!count.containsKey(el)) {
                nextEl = el;
            } else {
                count.put(el, count.get(el) - 1);
                if (count.get(el) == 0) {
                    count.remove(el);
                }
            }
        }
    }
}
