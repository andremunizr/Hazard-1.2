
$(document).on( 'click', '#toggle-login-button', toggleLoginBox );
$(document).on( 'click', '#titleFacadeEquip', toggleEquipBox );
$(document).on( 'click', '#menu-button', toggleOptionsBox );
$(document).on( 'click', '#commentTitle', toggleCommentsDatatable );
$(document).on( 'click', '#edit-task-title', toggleEditTaskTable );

function toggleLoginBox(){
    
    $('.loginBox').fadeToggle();    
    $('.focusable').first().focus();    
}

function toggleEquipBox(){
    
    $('#equipBox').slideToggle();
}

function toggleOptionsBox(){
    
    $('#options').fadeToggle();
}

function toggleCommentsDatatable(){
    
    $('.comments-datatable').fadeToggle();
}

function toggleEditTaskTable(){
    
    $('.edit-task-box').fadeToggle();
}