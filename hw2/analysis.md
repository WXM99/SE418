# Analysis of SJTU loading page

> *From the perspective of front-end*

## I. About Code Structure

### 1. Bad organization

Obviously., the code organization in this project did not achieve **decoupling and cohesion**. 

Except for some well-package widgets, the whole page is organized as a single file with a single long main function to init everything and a  single api to receive every information.

![image-20190331220110484](./analysis.assets/image-20190331220110484.png)
(put every thing in a single Main object and init them altogerther in the begining)



![image-20190331215826456](./analysis.assets/image-20190331215826456.png)
(all the information a recived as a single json from a single api) 

This kind of organization obviously violates the principle of **modularization** in modern software engineering.

### 2. Performance loss

As a certain result of the bad organization, performance loss is caused by the notoriously big object Main and its init() function.  When script run with it, every part and widgets will be loaded at one time, which extends the users' wating time and memory consuming. 

![image-20190331232438688](./analysis.assets/image-20190331232438688.png)

(Performance analysis proves it)

### 3. Suffering in maintenance

When checking the main.js where the Main object is defined, a mess of javascript code is shown. The defination of the Main object is upto **326 lines**. Each part of the inner code is also hard to read for it's Irregular indentation and poor comments.

In the index.html itself, a mount of helper function also defined without comments and perpose. Raw operations to strings fill thoes functions, making it much more difficlut to maintain this project.

![image-20190331221805815](./analysis.assets/image-20190331221805815.png)
(Ugly helper funtion in index.html)

### 4. Comparison



### 5. Optimizations





## II. About Project Specification

### 1. Directory structure

![image-20190331222016879](./analysis.assets/image-20190331222016879.png)

As is shown above, the directory structure of this project is a kind of messy. But the wrose is that the almost dupilcated directories appeared twice, causing unnecessary files transported in the network. So, the directory of this project is not clean and clear enough.

![image-20190331222527578](./analysis.assets/image-20190331222527578.png)

 

### 2. Programming specification

![image-20190331222726187](./analysis.assets/image-20190331222726187.png)
(part of the code)

There is a significant evidence shown above that this coding style of this project is not good.

Obviously it doesn't follow any programming specification except "freestyle". Also, no Static code checking tool like Eslint participating in the development.

![image-20190331223234252](./analysis.assets/image-20190331223234252.png)

![image-20190331223507493](./analysis.assets/image-20190331223507493.png)

(bad naming "dbcylj")

The names of files and directory are still cofusing. e.g. "dbcylj" is "底部常用连接".

### 3. Comparison

![image-20190331233044226](/Users/Miao/Projects/Github/SE418/hw2/analysis.assets/image-20190331233044226.png)



### 4. Optimizations

## III. About the Framework

### 1. MVC + Template rendering

It's for sure that no front-end frameworks are took part in. But it can be infered that the project   used a MVC framework with template rendering funtion. 

The evidence is the "dbcylj" directory. The index.html file here jest contains the bottom bar.

![image-20190331223902345](./analysis.assets/image-20190331223902345.png)
(dbcylj/index.html)

![image-20190331223943082](./analysis.assets/image-20190331223943082.png)
(the bottom bar)

Some php traces are also detected. So the MVC frame may be "laravel".

### 2. Make the best use of it?

About how good the Modal (M) and Controller (C) is used in the project is packaged in the back-end and invisible. But as for the View (V). The project didn't make the beat use. Because except for the poorly wirtten 'dbcylj' part in the bottom. No any other widgets are packaged and shown in the directory. It makes the template renderding almost useless.

### 3. Comparison

### 4. Optimizations



## IV. About the Deployment

### 1. Fully exposed

![image-20190331222016879](./analysis.assets/image-20190331222016879.png)

(a fully exposed directory)

Thanks to its shortcoming in deployment, The whole directory is totally exposed and just lay there to be analysis :). This is due to the absence of **webpack**. No optimization and compression was made when deploying. In other words, It looks more like a dev (developing) branch in the test server than a finished project.

### 2. Performance loss

The absence of webpack caused some resouce and code much larger than the compressed ones. 

![image-20190331225502225](./analysis.assets/image-20190331225502225.png)

![image-20190331225654188](./analysis.assets/image-20190331225654188.png)

(comparison between compressed one and the not compressed one)

The 'lib' file jQuery is richer but downloaded faster than the self-written traffic.js. This is the performance loss made by bad deployment.

### 3. Comparison

### 4. Optimizations

## V. About Resource Allocation

### 1. Bitmap

Except for dynamic images, Many static decorative images are almost big bitmaps like .jpg or .png. Thoes big files cost much when transporting and look blur when zooming. Even some simple lines and blocks are still stored in the /svg directory as bitmaps.

![image-20190331230407464](./analysis.assets/image-20190331230407464.png)

(png and jpg in ./svg) 

The incorrect resource format leads to a waste of network resouces.

### 2. Multi-language and localization

There is a English entance in the top-letf corner.

![image-20190331230655165](./analysis.assets/image-20190331230655165.png)

But when click it, the whole page refresh into a totally diffrent page with diffrent host name.

![image-20190331230855307](./analysis.assets/image-20190331230855307.png)

Many frameworks has integrated localization function that can make the same website fit diffrent languages conviniently. Write two separated website is a wasted of labor and the serve resouce.

### 3. Cost of rendering

![image-20190331231552534](./analysis.assets/image-20190331231552534.png)

Rendering the page cost the most calculating and memory resource in user-end of this page. 

![image-20190331231718170](./analysis.assets/image-20190331231718170.png)

Considering the visit of this page is not so large. Maybe part of the rendering work can be finished in back-end to optimize.

### 4. Comparison

### 5. Optimizations

## VI. Advantages

-  Unified UI concept
-  Adequate information
-  Low memory footprint

![image-20190331232125232](./analysis.assets/image-20190331232125232.png)



## VII. Conclusion
