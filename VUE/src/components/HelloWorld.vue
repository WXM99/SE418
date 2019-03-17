<template>
  <div class="hello">
    <Row>
      <Col :md="{span: 12, offset: 6}" :lg="{span: 8, offset: 8}" :xs="{span:22, offset: 1}" :sm="{span: 22, offset: 1}" >
        <Card style="margin-top: 100px" class="wd-card">
          <img src="../assets/wordLadder.png">
          <Row>
    <Input v-model="first_word" @on-change="validator1" @on-blur="check" style="margin-top: 40px">
      <span slot="prepend">&nbsp;&nbsp;1st word</span>
    </Input>
          </Row>
          <Row style="height: 25px; text-align: left; color: #ab5a4a">
            <span v-if="!valid1 && !input1">invalid input</span>
            <span v-if="valid1 && !input1" style="color: #65b55c">okay!</span>
          </Row>
          <Row>
    <Input v-model="second_word" @on-change="validator2" @on-blur="check">
      <span slot="prepend">2ed word</span>
    </Input>
          </Row>
          <Row style="height: 25px; text-align: left; color: #ab5a4a">
            <span v-if="!valid2 && !input2">invalid input</span>
            <span v-if="valid2 && !input2" style="color: #65b55c">okay!</span>
          </Row>
    <Button type="primary" v-if="!(this.valid)" disabled @click="generate" style="font-size: 20px">Generate</Button>
    <Button type="primary" v-if="this.valid" @click="generate" style="font-size: 20px">Generate</Button>
          <div v-if="dispaly" style="margin-top: 20px">
            <div v-if="this.chain === null">No ladder between {{this.first_word}}&{{this.second_word}}</div>
            <div v-else style="color: #fff; font-weight: bold">{{this.chain}}</div>
          </div>
        </Card>
      </Col>
    </Row>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'HelloWorld',
  data () {
    return {
      first_word: null,
      second_word: null,
      input1: true,
      input2: true,
      valid1: false,
      valid2: false,
      valid: false,
      chain: null,
      dispaly: false
    }
  },
  components: {
    axios
  },
  mounted () {
  },
  methods: {
    generate: function () {
      this.$Spin.show()
      this.$axios({
        method: 'post',
        url: 'http://localhost:9090/generate',
        data: {word1: this.first_word, word2: this.second_word}
      }).then(response => {
        console.log(response.data)
        this.chain = response.data
        this.$Spin.hide()
        this.dispaly = true
      })
    },
    validator1: function () {
      this.input1 = false
      this.$axios({
        method: 'post',
        url: 'http://localhost:9090/validate',
        data: {word: this.first_word}
      }).then(response => {
        this.valid1 = response.data === 1
      })
    },
    validator2: function () {
      this.input2 = false
      this.$axios({
        method: 'post',
        url: 'http://localhost:9090/validate',
        data: {word: this.second_word}
      }).then(response => {
        this.valid2 = response.data === 1
      })
    },
    check: function () {
      console.log(this.first_word.length, this.second_word.length)
      if (this.first_word.length === this.second_word.length) {
        console.log(this.valid1, this.valid2)
        if (this.valid1 && this.valid2) {
          this.valid = true
        }
      }
    }
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
  .hello{
    height: 100%;
  }
  .wd-card{
    border: 0px;
    background: rgba(237, 233, 255, 0.1);
    -webkit-backdrop-filter: saturate(180%) blur(5px);
    height: 400px;
    border-radius: 30px;
    margin-top: 200px;
    box-shadow: 0px 5px 20px rgb(27, 31, 38);
    padding: 20px;
  }
</style>
