var bCancel = false; 

    function validateTestNewsForm(form) { 
        if (bCancel) { 
            return true; 
        } else { 
            var formValidationResult; 
            formValidationResult = validateRequired(form) & validateMaxLength(form) & validateDate(form); 
            return (formValidationResult == 1); 
        } 
    } 

    function testNewsForm_required () { 
     this.a0 = new Array("title", msgTitleRequired , new Function ("varName", "this.maxlength='100';  return this[varName];"));
     this.a1 = new Array("brief", msgBriefRequired, new Function ("varName", "this.maxlength='500';  return this[varName];"));
     this.a2 = new Array("content", msgContentRequired, new Function ("varName", "this.maxlength='1000';  return this[varName];"));
     this.a3 = new Array("displayPostDate", msgDateRequired, new Function ("varName", "this.datePattern='dd-MM-yyyy';  return this[varName];"));
    } 

    function testNewsForm_maxlength () { 
     this.a0 = new Array("title", msgTitleIsBiger, new Function ("varName", "this.maxlength='100';  return this[varName];"));
     this.a1 = new Array("brief", msgBriefIsBiger, new Function ("varName", "this.maxlength='500';  return this[varName];"));
     this.a2 = new Array("content", msgContentIsBiger, new Function ("varName", "this.maxlength='1000';  return this[varName];"));
    } 

    function testNewsForm_DateValidations () { 
     this.a0 = new Array("displayPostDate", msgDateFormat, new Function ("varName", "this.datePattern='" + msgDatePattern + "';  return this[varName];"));
    } 



/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
   /*$RCSfile: validateDate.js,v $ $Rev: 478676 $ $Date: 2006-11-23 21:35:44 +0000 (Thu, 23 Nov 2006) $ */
    /**
    * Check to see if fields are a valid date.
    * Fields are not checked if they are disabled.
    * @param form The form validation is taking place on.
    */
    function validateDate(form) {
       var bValid = true;
       var focusField = null;
       var i = 0;
       var fields = new Array();
 
       var oDate = eval('new ' + jcv_retrieveFormName(form) +  '_DateValidations()');

       for (var x in oDate) {
            if (!jcv_verifyArrayElement(x, oDate[x])) {
                continue;
            }
           var field = form[oDate[x][0]];
           if (!jcv_isFieldPresent(field)) {
             continue;
           }
           var value = field.value;
           var isStrict = true;
           var datePattern = oDate[x][2]("datePatternStrict");
           // try loose pattern
           if (datePattern == null) {
               datePattern = oDate[x][2]("datePattern");
               isStrict = false;
           }    
           if ((field.type == 'hidden' ||
                field.type == 'text' ||
                field.type == 'textarea') &&
               (value.length > 0) && (datePattern.length > 0)) {
                 var MONTH = "MM";
                 var DAY = "dd";
                 var YEAR = "yyyy";
                 var orderMonth = datePattern.indexOf(MONTH);
                 var orderDay = datePattern.indexOf(DAY);
                 var orderYear = datePattern.indexOf(YEAR);
                 if ((orderDay < orderYear && orderDay > orderMonth)) {
                     var iDelim1 = orderMonth + MONTH.length;
                     var iDelim2 = orderDay + DAY.length;
                     var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
                     var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
                     if (iDelim1 == orderDay && iDelim2 == orderYear) {
                        dateRegexp = isStrict 
                             ? new RegExp("^(\\d{2})(\\d{2})(\\d{4})$") 
                             : new RegExp("^(\\d{1,2})(\\d{1,2})(\\d{4})$");
                     } else if (iDelim1 == orderDay) {
                        dateRegexp = isStrict 
                             ? new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$")
                             : new RegExp("^(\\d{1,2})(\\d{1,2})[" + delim2 + "](\\d{4})$");
                     } else if (iDelim2 == orderYear) {
                        dateRegexp = isStrict
                             ? new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$")
                             : new RegExp("^(\\d{1,2})[" + delim1 + "](\\d{1,2})(\\d{4})$");
                     } else {
                        dateRegexp = isStrict
                             ? new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$")
                             : new RegExp("^(\\d{1,2})[" + delim1 + "](\\d{1,2})[" + delim2 + "](\\d{4})$");
                     }
                     var matched = dateRegexp.exec(value);
                     if(matched != null) {
                        if (!jcv_isValidDate(matched[2], matched[1], matched[3])) {
                           if (i == 0) {
                               focusField = field;
                           }
                           fields[i++] = oDate[x][1];
                           bValid =  false;
                        }
                     } else {
                        if (i == 0) {
                            focusField = field;
                        }
                        fields[i++] = oDate[x][1];
                        bValid =  false;
                     }
                 } else if ((orderMonth < orderYear && orderMonth > orderDay)) {
                     var iDelim1 = orderDay + DAY.length;
                     var iDelim2 = orderMonth + MONTH.length;
                     var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
                     var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
                     if (iDelim1 == orderMonth && iDelim2 == orderYear) {
                         dateRegexp = isStrict 
                            ? new RegExp("^(\\d{2})(\\d{2})(\\d{4})$")
                            : new RegExp("^(\\d{1,2})(\\d{1,2})(\\d{4})$");
                     } else if (iDelim1 == orderMonth) {
                         dateRegexp = isStrict
                            ? new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$")
                            : new RegExp("^(\\d{1,2})(\\d{1,2})[" + delim2 + "](\\d{4})$");
                     } else if (iDelim2 == orderYear) {
                         dateRegexp = isStrict
                            ? new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$")
                            : new RegExp("^(\\d{1,2})[" + delim1 + "](\\d{1,2})(\\d{4})$");
                     } else {
                         dateRegexp = isStrict
                            ? new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$")
                            : new RegExp("^(\\d{1,2})[" + delim1 + "](\\d{1,2})[" + delim2 + "](\\d{4})$");
                     }
                     var matched = dateRegexp.exec(value);
                     if(matched != null) {
                         if (!jcv_isValidDate(matched[1], matched[2], matched[3])) {
                             if (i == 0) {
                                  focusField = field;
                             }
                             fields[i++] = oDate[x][1];
                             bValid =  false;
                          }
                     } else {
                         if (i == 0) {
                             focusField = field;
                         }
                         fields[i++] = oDate[x][1];
                         bValid =  false;
                     }
                 } else if ((orderMonth > orderYear && orderMonth < orderDay)) {
                     var iDelim1 = orderYear + YEAR.length;
                     var iDelim2 = orderMonth + MONTH.length;
                     var delim1 = datePattern.substring(iDelim1, iDelim1 + 1);
                     var delim2 = datePattern.substring(iDelim2, iDelim2 + 1);
                     if (iDelim1 == orderMonth && iDelim2 == orderDay) {
                         dateRegexp = isStrict
                            ? new RegExp("^(\\d{4})(\\d{2})(\\d{2})$")
                            : new RegExp("^(\\d{4})(\\d{1,2})(\\d{1,2})$");
                     } else if (iDelim1 == orderMonth) {
                         dateRegexp = isStrict
                            ? new RegExp("^(\\d{4})(\\d{2})[" + delim2 + "](\\d{2})$")
                            : new RegExp("^(\\d{4})(\\d{1,2})[" + delim2 + "](\\d{1,2})$");
                     } else if (iDelim2 == orderDay) {
                         dateRegexp = isStrict
                            ? new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})(\\d{2})$")
                            : new RegExp("^(\\d{4})[" + delim1 + "](\\d{1,2})(\\d{1,2})$");
                     } else {
                         dateRegexp = isStrict
                            ? new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{2})$")
                            : new RegExp("^(\\d{4})[" + delim1 + "](\\d{1,2})[" + delim2 + "](\\d{1,2})$");
                     }
                     var matched = dateRegexp.exec(value);
                     if(matched != null) {
                         if (!jcv_isValidDate(matched[3], matched[2], matched[1])) {
                             if (i == 0) {
                                 focusField = field;
                             }
                             fields[i++] = oDate[x][1];
                             bValid =  false;
                         }
                     } else {
                          if (i == 0) {
                              focusField = field;
                          }
                          fields[i++] = oDate[x][1];
                          bValid =  false;
                     }
                 } else {
                     if (i == 0) {
                         focusField = field;
                     }
                     fields[i++] = oDate[x][1];
                     bValid =  false;
                 }
          }
       }
       if (fields.length > 0) {
          jcv_handleErrors(fields, focusField);
       }
       return bValid;
    }
    
    function jcv_isValidDate(day, month, year) {
	    if (month < 1 || month > 12) {
            return false;
        }
        if (day < 1 || day > 31) {
            return false;
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) &&
            (day == 31)) {
            return false;
        }
        if (month == 2) {
            var leap = (year % 4 == 0 &&
               (year % 100 != 0 || year % 400 == 0));
            if (day>29 || (day == 29 && !leap)) {
                return false;
            }
        }
        return true;
    }

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
  /*$RCSfile: validateUtilities.js,v $ $Rev: 478676 $ $Date: 2006-11-23 21:35:44 +0000 (Thu, 23 Nov 2006) $ */
  /**
  * This is a place holder for common utilities used across the javascript validation
  *
  **/

  /**
   * Retreive the name of the form
   * @param form The form validation is taking place on.
   */
  function jcv_retrieveFormName(form) {

      // Please refer to Bugs 31534, 35127, 35294, 37315 & 38159
      // for the history of the following code

      var formName;

      if (form.getAttributeNode) {
          if (form.getAttributeNode("id") && form.getAttributeNode("id").value) {
              formName = form.getAttributeNode("id").value;
          } else {
              formName = form.getAttributeNode("name").value;
          }
      } else if (form.getAttribute) {
          if (form.getAttribute("id")) {
              formName = form.getAttribute("id");
          } else {
              formName = form.attributes["name"];
          }
      } else {
          if (form.id) {
              formName = form.id;
          } else {
              formName = form.name;
          }
      }

      return formName;

  }  

  /**
   * Handle error messages.
   * @param messages Array of error messages.
   * @param focusField Field to set focus on.
   */
  function jcv_handleErrors(messages, focusField) {
      if (focusField && focusField != null) {
          var doFocus = true;
          if (focusField.disabled || focusField.type == 'hidden') {
              doFocus = false;
          }
          if (doFocus && 
              focusField.style && 
              focusField.style.visibility &&
              focusField.style.visibility == 'hidden') {
              doFocus = false;
          }
          if (doFocus) {
              focusField.focus();
          }
      }
      alert(messages.join('\n'));
  }

  /**
   * Checks that the array element is a valid
   * Commons Validator element and not one inserted by
   * other JavaScript libraries (for example the
   * prototype library inserts an "extends" into
   * all objects, including Arrays).
   * @param name The element name.
   * @param value The element value.
   */
  function jcv_verifyArrayElement(name, element) {
      if (element && element.length && element.length == 3) {
          return true;
      } else {
          return false;
      }
  }

  /**
   * Checks whether the field is present on the form.
   * @param field The form field.
   */
  function jcv_isFieldPresent(field) {
      var fieldPresent = true;
      if (field == null || (typeof field == 'undefined')) {
          fieldPresent = false;
      } else {
          if (field.disabled) {
              fieldPresent = false;
          }
      }
      return fieldPresent;
  }

  /**
   * Check a value only contains valid numeric digits
   * @param argvalue The value to check.
   */
  function jcv_isAllDigits(argvalue) {
      argvalue = argvalue.toString();
      var validChars = "0123456789";
      var startFrom = 0;
      if (argvalue.substring(0, 2) == "0x") {
         validChars = "0123456789abcdefABCDEF";
         startFrom = 2;
      } else if (argvalue.charAt(0) == "0") {
         validChars = "01234567";
         startFrom = 1;
      } else if (argvalue.charAt(0) == "-") {
          startFrom = 1;
      }

      for (var n = startFrom; n < argvalue.length; n++) {
          if (validChars.indexOf(argvalue.substring(n, n+1)) == -1) return false;
      }
      return true;
  }

  /**
   * Check a value only contains valid decimal digits
   * @param argvalue The value to check.
   */
  function jcv_isDecimalDigits(argvalue) {
      argvalue = argvalue.toString();
      var validChars = "0123456789";

      var startFrom = 0;
      if (argvalue.charAt(0) == "-") {
          startFrom = 1;
      }

      for (var n = startFrom; n < argvalue.length; n++) {
          if (validChars.indexOf(argvalue.substring(n, n+1)) == -1) return false;
      }
      return true;
  }

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
    /*$RCSfile: validateMaxLength.js,v $ $Rev: 478676 $ $Date: 2006-11-23 21:35:44 +0000 (Thu, 23 Nov 2006) $ */
    /**
    * A field is considered valid if less than the specified maximum.
    * Fields are not checked if they are disabled.
    *
    *  Caution: Using validateMaxLength() on a password field in a 
    *  login page gives unnecessary information away to hackers. While it only slightly
    *  weakens security, we suggest using it only when modifying a password.
    * @param form The form validation is taking place on.
    */
    function validateMaxLength(form) {
        var isValid = true;
        var focusField = null;
        var i = 0;
        var fields = new Array();
 
        var oMaxLength = eval('new ' + jcv_retrieveFormName(form) +  '_maxlength()');        
        for (var x in oMaxLength) {
            if (!jcv_verifyArrayElement(x, oMaxLength[x])) {
                continue;
            }
            var field = form[oMaxLength[x][0]];
            if (!jcv_isFieldPresent(field)) {
              continue;
            }

            if ((field.type == 'hidden' ||
                field.type == 'text' ||
                field.type == 'password' ||
                field.type == 'textarea')) {

                /* Adjust length for carriage returns - see Bug 37962 */
                var lineEndLength = oMaxLength[x][2]("lineEndLength");
                var adjustAmount = 0;
                if (lineEndLength) {
                    var rCount = 0;
                    var nCount = 0;
                    var crPos = 0;
                    while (crPos < field.value.length) {
                        var currChar = field.value.charAt(crPos);
                        if (currChar == '\r') {
                            rCount++;
                        }
                        if (currChar == '\n') {
                            nCount++;
                        }
                        crPos++;
                    }
                    var endLength = parseInt(lineEndLength);
                    adjustAmount = (nCount * endLength) - (rCount + nCount);
                }

                var iMax = parseInt(oMaxLength[x][2]("maxlength"));
                if ((field.value.length + adjustAmount)  > iMax) {
                    if (i == 0) {
                        focusField = field;
                    }
                    fields[i++] = oMaxLength[x][1];
                    isValid = false;
                }
            }
        }
        if (fields.length > 0) {
           jcv_handleErrors(fields, focusField);
        }
        return isValid;
    }

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
    /*$RCSfile: validateRequired.js,v $ $Rev: 478676 $ $Date: 2006-11-23 21:35:44 +0000 (Thu, 23 Nov 2006) $ */
    /**
    *  Check to see if fields must contain a value.
    * Fields are not checked if they are disabled.
    *
    * @param form The form validation is taking place on.
    */

    function validateRequired(form) {
        var isValid = true;
        var focusField = null;
        var i = 0;
        var fields = new Array();

        var oRequired = eval('new ' + jcv_retrieveFormName(form) +  '_required()');

        for (var x in oRequired) {
            if (!jcv_verifyArrayElement(x, oRequired[x])) {
                continue;
            }
            var field = form[oRequired[x][0]];

            if (!jcv_isFieldPresent(field)) {
                fields[i++] = oRequired[x][1];
                isValid=false;
            } else if ((field.type == 'hidden' ||
                field.type == 'text' ||
                field.type == 'textarea' ||
                field.type == 'file' ||
                field.type == 'radio' ||
                field.type == 'checkbox' ||
                field.type == 'select-one' ||
                field.type == 'password')) {

                var value = '';
                // get field's value
                if (field.type == "select-one") {
                    var si = field.selectedIndex;
                    if (si >= 0) {
                        value = field.options[si].value;
                    }
                } else if (field.type == 'radio' || field.type == 'checkbox') {
                    if (field.checked) {
                        value = field.value;
                    }
                } else {
                    value = field.value;
                }

                if (trim(value).length == 0) {

                    if ((i == 0) && (field.type != 'hidden')) {
                        focusField = field;
                    }
                    fields[i++] = oRequired[x][1];
                    isValid = false;
                }
            } else if (field.type == "select-multiple") { 
                var numOptions = field.options.length;
                lastSelected=-1;
                for(loop=numOptions-1;loop>=0;loop--) {
                    if(field.options[loop].selected) {
                        lastSelected = loop;
                        value = field.options[loop].value;
                        break;
                    }
                }
                if(lastSelected < 0 || trim(value).length == 0) {
                    if(i == 0) {
                        focusField = field;
                    }
                    fields[i++] = oRequired[x][1];
                    isValid=false;
                }
            } else if ((field.length > 0) && (field[0].type == 'radio' || field[0].type == 'checkbox')) {
                isChecked=-1;
                for (loop=0;loop < field.length;loop++) {
                    if (field[loop].checked) {
                        isChecked=loop;
                        break; // only one needs to be checked
                    }
                }
                if (isChecked < 0) {
                    if (i == 0) {
                        focusField = field[0];
                    }
                    fields[i++] = oRequired[x][1];
                    isValid=false;
                }
            }   
        }
        if (fields.length > 0) {
           jcv_handleErrors(fields, focusField);
        }
        return isValid;
    }
    
    // Trim whitespace from left and right sides of s.
    function trim(s) {
        return s.replace( /^\s*/, "" ).replace( /\s*$/, "" );
    }
