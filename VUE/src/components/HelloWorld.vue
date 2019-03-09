<template>
  <div class="hello">

  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      res: null
    }
  },
  components: {
    axios
  },
  mounted () {
    this.$axios({
      method: 'post',
      url: 'http://localhost:9090/generate',
      data: {word1: 'code', word2: 'hello'}
    }).then(response => {
      console.log(response.data())
    })
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
