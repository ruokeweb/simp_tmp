(global["webpackJsonp"] = global["webpackJsonp"] || []).push([["pages_mine/infomation/addr/addrDetail"],{

/***/ 390:
/*!***************************************************************************************************!*\
  !*** E:/workspace/simp/simp_wc_sp/main.js?{"page":"pages_mine%2Finfomation%2Faddr%2FaddrDetail"} ***!
  \***************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(createPage) {__webpack_require__(/*! uni-pages */ 4);__webpack_require__(/*! @dcloudio/uni-stat */ 5);

var _vue = _interopRequireDefault(__webpack_require__(/*! vue */ 2));
var _addrDetail = _interopRequireDefault(__webpack_require__(/*! ./pages_mine/infomation/addr/addrDetail.vue */ 391));function _interopRequireDefault(obj) {return obj && obj.__esModule ? obj : { default: obj };}
createPage(_addrDetail.default);
/* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(/*! ./node_modules/@dcloudio/uni-mp-weixin/dist/index.js */ 1)["createPage"]))

/***/ }),

/***/ 391:
/*!******************************************************************************!*\
  !*** E:/workspace/simp/simp_wc_sp/pages_mine/infomation/addr/addrDetail.vue ***!
  \******************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _addrDetail_vue_vue_type_template_id_57beebda___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./addrDetail.vue?vue&type=template&id=57beebda& */ 392);
/* harmony import */ var _addrDetail_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./addrDetail.vue?vue&type=script&lang=js& */ 394);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _addrDetail_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _addrDetail_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var _addrDetail_vue_vue_type_style_index_0_lang_css___WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./addrDetail.vue?vue&type=style&index=0&lang=css& */ 396);
/* harmony import */ var _C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./node_modules/vue-loader/lib/runtime/componentNormalizer.js */ 19);






/* normalize component */

var component = Object(_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_3__["default"])(
  _addrDetail_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__["default"],
  _addrDetail_vue_vue_type_template_id_57beebda___WEBPACK_IMPORTED_MODULE_0__["render"],
  _addrDetail_vue_vue_type_template_id_57beebda___WEBPACK_IMPORTED_MODULE_0__["staticRenderFns"],
  false,
  null,
  null,
  null
  
)

/* hot reload */
if (false) { var api; }
component.options.__file = "E:/workspace/simp/simp_wc_sp/pages_mine/infomation/addr/addrDetail.vue"
/* harmony default export */ __webpack_exports__["default"] = (component.exports);

/***/ }),

/***/ 392:
/*!*************************************************************************************************************!*\
  !*** E:/workspace/simp/simp_wc_sp/pages_mine/infomation/addr/addrDetail.vue?vue&type=template&id=57beebda& ***!
  \*************************************************************************************************************/
/*! exports provided: render, staticRenderFns */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_hbuilderx_packages_webpack_uni_nvue_loader_lib_templateLoader_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_17_0_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_index_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_custom_block_loader_index_js_ref_0_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_addrDetail_vue_vue_type_template_id_57beebda___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!./node_modules/@dcloudio/vue-cli-plugin-hbuilderx/packages/webpack-uni-nvue-loader/lib/templateLoader.js??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--17-0!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/template.js!./node_modules/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-custom-block-loader??ref--0-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./addrDetail.vue?vue&type=template&id=57beebda& */ 393);
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "render", function() { return _C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_hbuilderx_packages_webpack_uni_nvue_loader_lib_templateLoader_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_17_0_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_index_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_custom_block_loader_index_js_ref_0_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_addrDetail_vue_vue_type_template_id_57beebda___WEBPACK_IMPORTED_MODULE_0__["render"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "staticRenderFns", function() { return _C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_hbuilderx_packages_webpack_uni_nvue_loader_lib_templateLoader_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_17_0_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_index_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_custom_block_loader_index_js_ref_0_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_addrDetail_vue_vue_type_template_id_57beebda___WEBPACK_IMPORTED_MODULE_0__["staticRenderFns"]; });



/***/ }),

/***/ 393:
/*!************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/@dcloudio/vue-cli-plugin-hbuilderx/packages/webpack-uni-nvue-loader/lib/templateLoader.js??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--17-0!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/template.js!./node_modules/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-custom-block-loader??ref--0-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!E:/workspace/simp/simp_wc_sp/pages_mine/infomation/addr/addrDetail.vue?vue&type=template&id=57beebda& ***!
  \************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! exports provided: render, staticRenderFns */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "render", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "staticRenderFns", function() { return staticRenderFns; });
var render = function() {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
}
var staticRenderFns = []
render._withStripped = true



/***/ }),

/***/ 394:
/*!*******************************************************************************************************!*\
  !*** E:/workspace/simp/simp_wc_sp/pages_mine/infomation/addr/addrDetail.vue?vue&type=script&lang=js& ***!
  \*******************************************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_index_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_custom_block_loader_index_js_ref_0_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_addrDetail_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!./node_modules/babel-loader/lib!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--12-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/script.js!./node_modules/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-custom-block-loader??ref--0-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./addrDetail.vue?vue&type=script&lang=js& */ 395);
/* harmony import */ var _C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_index_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_custom_block_loader_index_js_ref_0_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_addrDetail_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_index_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_custom_block_loader_index_js_ref_0_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_addrDetail_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_index_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_custom_block_loader_index_js_ref_0_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_addrDetail_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_index_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_custom_block_loader_index_js_ref_0_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_addrDetail_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__[key]; }) }(__WEBPACK_IMPORT_KEY__));
 /* harmony default export */ __webpack_exports__["default"] = (_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_index_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_custom_block_loader_index_js_ref_0_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_addrDetail_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0___default.a); 

/***/ }),

/***/ 395:
/*!*******************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/babel-loader/lib!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--12-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/script.js!./node_modules/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-custom-block-loader??ref--0-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!E:/workspace/simp/simp_wc_sp/pages_mine/infomation/addr/addrDetail.vue?vue&type=script&lang=js& ***!
  \*******************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(uni) {Object.defineProperty(exports, "__esModule", { value: true });exports.default = void 0;









































var _app = _interopRequireDefault(__webpack_require__(/*! @/common/app.js */ 12));
var _request = _interopRequireDefault(__webpack_require__(/*! @/common/request.js */ 27));
var _loadcache = _interopRequireDefault(__webpack_require__(/*! @/common/loadcache.js */ 26));function _interopRequireDefault(obj) {return obj && obj.__esModule ? obj : { default: obj };} //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
var multipleLinkage = function multipleLinkage() {return __webpack_require__.e(/*! import() | components/multipleLlinkage/multiple-linkage */ "components/multipleLlinkage/multiple-linkage").then(__webpack_require__.bind(null, /*! @/components/multipleLlinkage/multiple-linkage.vue */ 102));}; //多级联动
var _default = { components: { multipleLinkage: multipleLinkage }, data: function data() {return { "id": "", "district": "", "province": "", "city": "", "country": "", "detail": "", "zipcode": "", remark: "", showTitle: "联系地址", dialogTitle: "请选择联系地址", addArry: {}, flag: false, "showAddList": [], typeIndex: 0, typeArr: [], saveFlag: false, deleteFlag: false, hasDelete: false };}, onLoad: function onLoad(option) {//加载基础数据
    // this.nameArr = cache.getvaluetokey('POLITICS_NAME');
    if (null != option) {console.log(option.id);this.id = option.id;if (this.id != 0) {this.hasDelete = true;}}}, onShow: function onShow() {var _this = this; //获取类型基础数据
    var typeArrTem = _loadcache.default.getDictsByTypeCode(_app.default.DICT_TYPECODE.IS_NATION);if (typeArrTem.length != 0) {this.typeArr = typeArrTem;} else {
      this.typeArr = _loadcache.default.getDictsByTypeCode(_app.default.DICT_TYPECODE.IS_NATION);
    }

    this.addArry = uni.getStorageSync("multipleLinkageAddress").childs;
    if (null != this.id && this.id != '' && this.id != 0) {
      //获取地址基础信息
      _request.default.http({
        url: '/app/address/getByid',

        header: 'application/x-www-form-urlencoded',
        data: {
          id: this.id },

        success: function success(res) {
          if (res.data.code == _app.default.RESPONSE_STATUS.REQUEST_SUCCESS) {
            // console.log("成功获取地址基础数据")
            _this.district = res.data.data.district;
            _this.province = res.data.data.province;
            _this.city = res.data.data.city;
            _this.country = res.data.data.country;
            _this.detail = res.data.data.detail;
            _this.zipcode = res.data.data.zipcode;
            _this.remark = res.data.data.remark;
            var type = res.data.data.type;
            if (type != null && type != "") {
              for (var i = 0; i < _this.typeArr.length; i++) {
                if (_this.typeArr[i].value == type) {
                  _this.typeIndex = i;
                }
              }
            }

            var countryTem = '';
            if (null != _this.country && '' != _this.country) {
              countryTem = _loadcache.default.getObjFromStorageById(_app.default.CACHE_NAME.AREABASE, _this.country);
            }
            //countryTem = (null != countryTem || "" != countryTem ||"undefined"!=countryTem) ? countryTem : "";
            var cityTem = ''; //loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE, this.city);
            if (null != _this.city && '' != _this.city) {
              cityTem = _loadcache.default.getObjFromStorageById(_app.default.CACHE_NAME.AREABASE, _this.city);
            }
            // cityTem = (null != cityTem || "" != cityTem ||"undefined"!=cityTem) ? cityTem : "";
            var provinceTem = ''; //loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE, this.province);
            if (null != _this.province && '' != _this.province) {
              provinceTem = _loadcache.default.getObjFromStorageById(_app.default.CACHE_NAME.AREABASE, _this.
              province);
            }
            // provinceTem = (null != provinceTem || "" != provinceTem ||"undefined"!=provinceTem) ? provinceTem : "";
            var districtTem = ''; //loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE, this.district);
            //districtTem = (null != districtTem || "" != districtTem ||"undefined"!=districtTem) ? districtTem : "";							 
            if (null != _this.district && '' != _this.district) {
              districtTem = _loadcache.default.getObjFromStorageById(_app.default.CACHE_NAME.AREABASE, _this.
              district);
            }
            _this.showTitle = countryTem + provinceTem + cityTem + districtTem;
            _this.showAddList = [{
              'id': _this.country,
              'name': countryTem },
            {
              'id': _this.province,
              'name': provinceTem },
            {
              'id': _this.city,
              'name': cityTem },
            {
              'id': _this.district,
              'name': districtTem }];

          }
        },
        fail: function fail() {},
        complete: function complete() {
          _this.flag = true;
        } });

    } else {
      this.flag = true;
    }
  },
  methods: {
    bindPickerChangeType: function bindPickerChangeType(e) {
      this.typeIndex = e.target.value;
    },
    savedata: function savedata(e) {
      console.log(e);
      this.showTitle = e[5];
      this.country = e[0];
      this.province = e[1];
      this.city = e[2];
      this.district = e[3];
    },
    saveProfession: function saveProfession() {var _this2 = this;
      this.saveFlag = true;
      if (null == this.country || '' == this.country) {
        this.saveFlag = false;
        uni.showToast({
          icon: 'none',
          title: '请选择地址' });

        return false;
      }
      if (null == this.detail || '' == this.detail) {
        this.saveFlag = false;
        uni.showToast({
          icon: 'none',
          title: '请输入详细地址' });

        return false;
      }
      _request.default.http({
        url: "/app/address/save",
        header: 'application/x-www-form-urlencoded',
        data: {
          id: this.id,
          "remark": this.remark,
          "district": this.district,
          "province": this.province,
          "city": this.city,
          "country": this.country,
          "detail": this.detail,
          "zipcode": this.zipcode,
          "type": this.typeArr[this.typeIndex].value },

        success: function success(res) {
          if (res.code = _app.default.RESPONSE_STATUS.REQUEST_SUCCESS) {
            _this2.professionList = res.data.data;
            uni.navigateBack({});


            uni.showToast({
              icon: "none",
              title: "保存成功" });

          }
          _this2.saveFlag = false;
        },
        fail: function fail(data, code) {
          console.log(data);
          _this2.saveFlag = false;
        } });

    },
    deleteProfession: function deleteProfession() {var _this3 = this;
      this.deleteFlag = true;
      _request.default.http({
        url: "/app/address/delete",
        header: 'application/x-www-form-urlencoded',
        data: {
          id: this.id },

        success: function success(res) {
          if (res.code = _app.default.RESPONSE_STATUS.REQUEST_SUCCESS) {
            uni.showToast({
              icon: "none",
              title: "删除成功" });

            uni.navigateBack({
              url: "addr" });


          }
          _this3.deleteFlag = false;
        },
        fail: function fail(data, code) {
          console.log(data);
          _this3.deleteFlag = false;
        } });

    } } };exports.default = _default;
/* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(/*! ./node_modules/@dcloudio/uni-mp-weixin/dist/index.js */ 1)["default"]))

/***/ }),

/***/ 396:
/*!***************************************************************************************************************!*\
  !*** E:/workspace/simp/simp_wc_sp/pages_mine/infomation/addr/addrDetail.vue?vue&type=style&index=0&lang=css& ***!
  \***************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_index_js_ref_6_oneOf_1_2_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_loaders_stylePostLoader_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_index_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_custom_block_loader_index_js_ref_0_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_addrDetail_vue_vue_type_style_index_0_lang_css___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!./node_modules/mini-css-extract-plugin/dist/loader.js??ref--6-oneOf-1-0!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--6-oneOf-1-1!./node_modules/css-loader??ref--6-oneOf-1-2!./node_modules/vue-loader/lib/loaders/stylePostLoader.js!./node_modules/postcss-loader/src??ref--6-oneOf-1-3!./node_modules/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-custom-block-loader??ref--0-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./addrDetail.vue?vue&type=style&index=0&lang=css& */ 397);
/* harmony import */ var _C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_index_js_ref_6_oneOf_1_2_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_loaders_stylePostLoader_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_index_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_custom_block_loader_index_js_ref_0_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_addrDetail_vue_vue_type_style_index_0_lang_css___WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_index_js_ref_6_oneOf_1_2_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_loaders_stylePostLoader_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_index_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_custom_block_loader_index_js_ref_0_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_addrDetail_vue_vue_type_style_index_0_lang_css___WEBPACK_IMPORTED_MODULE_0__);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_index_js_ref_6_oneOf_1_2_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_loaders_stylePostLoader_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_index_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_custom_block_loader_index_js_ref_0_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_addrDetail_vue_vue_type_style_index_0_lang_css___WEBPACK_IMPORTED_MODULE_0__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_index_js_ref_6_oneOf_1_2_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_loaders_stylePostLoader_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_index_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_custom_block_loader_index_js_ref_0_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_addrDetail_vue_vue_type_style_index_0_lang_css___WEBPACK_IMPORTED_MODULE_0__[key]; }) }(__WEBPACK_IMPORT_KEY__));
 /* harmony default export */ __webpack_exports__["default"] = (_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_index_js_ref_6_oneOf_1_2_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_loaders_stylePostLoader_js_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_vue_loader_lib_index_js_vue_loader_options_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_custom_block_loader_index_js_ref_0_1_C_Users_Administrator_Downloads_HBuilderX_1_9_3_20190422_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_addrDetail_vue_vue_type_style_index_0_lang_css___WEBPACK_IMPORTED_MODULE_0___default.a); 

/***/ }),

/***/ 397:
/*!**********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/mini-css-extract-plugin/dist/loader.js??ref--6-oneOf-1-0!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--6-oneOf-1-1!./node_modules/css-loader??ref--6-oneOf-1-2!./node_modules/vue-loader/lib/loaders/stylePostLoader.js!./node_modules/postcss-loader/src??ref--6-oneOf-1-3!./node_modules/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-custom-block-loader??ref--0-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!E:/workspace/simp/simp_wc_sp/pages_mine/infomation/addr/addrDetail.vue?vue&type=style&index=0&lang=css& ***!
  \**********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin

/***/ })

},[[390,"common/runtime","common/vendor"]]]);
//# sourceMappingURL=../../../../.sourcemap/mp-weixin/pages_mine/infomation/addr/addrDetail.js.map