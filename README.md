# Listo

A simple project to make recyclerView adapting easier. By using **Listo** you **don't need to create any adapter** or view holder class for your recyclerView.

#### Installation:
Simply copy the [Listo.java](https://github.com/mahdi-malv/Listo/blob/master/malvrecycler/src/main/java/ir/malv/malvrecycler/Listo.java) from the project and add it to your project.

#### Usage:

```java
new Listo.Builder<String>()
    .load(recyclerView) // Pass the RecyclerView instance
    .withListOf(list) // Pass the List of Model (In this case String)
    .layout(R.layout.select_dialog_item) // Pass the layout for list items
    .setHasFixedSize(true)
    .layoutManager(new LinearLayoutManager(this))
    .withEveryItem(new Listo.WithEveryItemListener<String>() {
        @Override
        public void doOnCallback(String data, View itemView, int position) {
            // Here you have an item and you findViewById your views and do stuff with them
            TextView t = itemView.findViewById(R.id.text1);
            t.setText("Data: " + data + " Pos: " + position);
        }
    })
    .onClick(new Listo.ListItemClick() {
        @Override
        public void onClick(View view, int position) {
            // On click listener for list items
            Toast.makeText(MainActivity.this, "Hello " + position, Toast.LENGTH_SHORT).show();
        }
    })
    .build(); // Don't forget to call build at the end.
```

#### Example:
Clone this repo by entering this command in your command line (Have git installed):<br>
$ `git clone https://github.com/mahdi-malv/Listo.git`
