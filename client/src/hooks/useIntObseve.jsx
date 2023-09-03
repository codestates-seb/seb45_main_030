function useIntObserve(element) {
    let isScrollEnd;
    const io = new IntersectionObserver((entries, observer) => {})
    io.observe(element)
    return {isScrollEnd}
}
export default useIntObserve;
