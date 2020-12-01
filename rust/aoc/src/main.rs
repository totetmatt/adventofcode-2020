
use std::fs;

fn main() {
    let contents = fs::read_to_string("../../input01")
        .expect("Something went wrong reading the file");

    let  mut data : Vec<i32> =contents.
    split('\n').
    map( |x| x.parse::<i32>().unwrap())
    .collect();

    data.sort();
    println!("{:?}",data);
 
    for x in (1 .. data.len() ).rev() {
        let last : i32 = data[x];
        let diff = 2020 - last;
        let found = data.binary_search(&diff);
       
        match found {
            Ok(x) => println!("{:?} {:?} {:?} {:?}",data[x],last,data[x]+last,data[x]*last) ,
            Err(_) =>  () ,
        }
    }
    
}