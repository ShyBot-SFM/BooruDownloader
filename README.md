# BooruDownloader
### What is BooruDownloader?
BooruDownloader is a command line program that allows query (search) based downloads from different boorus.

## [Latest release](https://github.com/Jormii/BooruDownloader/releases/tag/v1.0)

## What is needed to run BooruDownloader?
A Java installation that supports Java 8 or above.

## What boorus are supported?
By default every standard booru is supported (see http://booru.org/).
Additionally, BooruDownloader supports:
- https://e621.net/
- https://rule34.paheal.net/

## Why is the program stuck in "Fetching URLs..."?
Some boorus, such as https://e621.net/ or https://rule34.xxx/ provide an API that allows faster URL fetching. However, the default booru does not provide those eases. For each page returned from a query, the program has to retrieve and parse the HTML of that page plus the HTMLs corresponding to each picture. This is a really slow process, so in case you're trying to download from a booru not under the next list, you have to be patient.
### Sped up boorus
- https://rule34.xxx/
- https://rule34.paheal.net/
- https://e621.net/

## How to use BooruDowloader
BooruDownloader needs:
- The BooruDownloader.jar file.
- The BooruQueries.txt file. BooruDownloader package includes a sample BooruQueries.txt file. This file is also created if it does not exist.

### BooruQueries.txt file format
#### "#" character
A line starting with a "#" is treated as a comment, and thus ignored by the program.

#### Queries
To indicate the program what it has to download, you have to provide the next information:
- A booru's URL to its home page, preceeded by a ">" character. For example: ">https://rule34.xxx/". Please provide the full URL, including the final "/". URLs such as "rule34.xxx/" or "https://rule34.xxx" won't work.
- A list of queries under the booru's URL, each in a different line. You have to type the queries as if you were typing them in the booru's search bar.
- You can also indicate a list of queries under a ">" character followed by no URL to indicate the program that those queries must be searched in every booru provided in the file. Important: If the same query is specified under a booru and under common queries, the program searches the former.

##### Query arguments
Additionaly, you can add properties to a query using different flags. These are:
- "--f": The program ignores if the files that match that query where previously cached, forcing their download.
- "--c": The program downloads no files. Instead, it only stores in cache the URLs of the files that match that query.
