function map() {
    var this_unique_visitor_ids = [];
    var this_unique_visitor = 0;
    var this_unique_id = this.open_id;

    if(this_unique_id !== '') {
        this_unique_visitor = 1;
        this_unique_visitor_ids = this_unique_visitor_ids.concat(this_unique_id);
    }

    emit({
        game_id: this.game_id,
        test: this.test
    }, {
        unique_visitor: this_unique_visitor,
        unique_visitor_ids: this_unique_visitor_ids
    });
}