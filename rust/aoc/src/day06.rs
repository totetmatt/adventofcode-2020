use std::collections::HashSet;
use std::fs;
pub fn day06() {
    fn part01(s: &str) -> usize {
        s.split("\n")
            .map(|x| x.chars().collect::<Vec<char>>())
            .flatten()
            .into_iter()
            .collect::<HashSet<char>>()
            .len()
    }
    fn part02(s: &str) -> usize {
        let q: Vec<HashSet<char>> = s
            .split("\n")
            .map(|x| x.chars().collect::<HashSet<char>>())
            .collect();
        let a: &HashSet<char> = q.first().unwrap();
        q.iter()
            .fold(a, |agg, x| agg.intersection(&x).collect::<HashSet<_>>())
            .len()
    }
    let contents =
        fs::read_to_string("..\\..\\input06").expect("Something went wrong reading the file");
    let forms = contents.split("\n\n");
    let part01: usize = forms.map(|x| part01(x)).sum::<usize>();
    println!("{:?}", part01);

    let part02: usize = forms.map(|x| part02(x)).sum::<usize>();
    println!("{:?}", part02);
}
