function map() {
    emit({
        city: this.city,
        house_type: this.house_type,
        unique_id: this.unique_id,
        layout_range: this.layout_range
    }, { view_count: this.view_count, mark: this.mark });
}