(function($){
        $.extend($.fn,{
            mask: function(msg,maskDivClass){
                this.unmask();
                // 参数
                var op = {
                    opacity: 0.1,
                    z: 10000,
                    bgcolor: '#ccc'
                };
                var original=$(document.body);
                var position={top:0,left:0};
                if(this[0] && this[0]!==window.document){
                    original=this;
                    position=original.position();
                }
                // 创建一个 Mask的父层，追加到body对象中
                var totalDiv=$('<div id="totalDivId" oncontextmenu="return false">&nbsp;</div>');
                totalDiv.appendTo(original);
                
                // 创建一个 Mask 层，追加到 Mask的父层中
                var maskDiv=$('<div class="maskdivgen">&nbsp;</div>');
                maskDiv.appendTo(totalDiv);

                var maskWidth=original.outerWidth();
                if(!maskWidth){
                    maskWidth=original.width();
                }

                var maskHeight=$(window.document).height();
                
                maskDiv.css({
                    position: 'absolute',
                    top: position.top,
                    left: position.left,
                    'z-index': op.z,
                    width: maskWidth,
                    height:maskHeight,
                    'background-color': op.bgcolor,
                    opacity: 0
                });
                if(maskDivClass){
                    maskDiv.addClass(maskDivClass);
                }
                if(msg){
                    var msgDiv=$('<div style="position:absolute"><div>'+msg+'</div></div>');
                    msgDiv.appendTo(totalDiv);
                    var widthspace=(maskDiv.width()-msgDiv.width());
                    var heightspace=(maskDiv.height()-msgDiv.height());
                    msgDiv.css({
                                cursor:'wait',
                                top:(heightspace/2-2),
                                left:(widthspace/2-2)
                      });
                  }
                  maskDiv.fadeIn('fast', function(){
                    // 淡入淡出效果
                    $(this).fadeTo('fast', op.opacity);
                })
                return maskDiv;
            },
         unmask: function(){
                     var original=$(document.body);
                         if(this[0] && this[0]!==window.document){
                            original=$(this[0]);
                      }
                      original.find("> div#totalDivId").fadeOut('fast',0,function(){
                          $(this).remove();
                      });
            }
        });
    })(jQuery);